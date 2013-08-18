package org.glydar.glydar.netty.packet.shared;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.glydar.api.data.EntityData;
import org.glydar.api.event.EventManager;
import org.glydar.api.event.events.EntityHealthEvent;
import org.glydar.api.event.events.EntityMoveEvent;
import org.glydar.api.event.events.EntityUpdateEvent;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.BaseTarget;
import org.glydar.glydar.models.EveryoneTarget;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.server.Packet2UpdateFinished;
import org.glydar.glydar.util.Bitops;
import org.glydar.glydar.util.ZLibOperations;

import sun.security.util.BitArray;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteOrder;
import java.util.Random;

@CubeWorldPacket.Packet(id = 0, variableLength = true)
public class Packet0EntityUpdate extends CubeWorldPacket {
	 byte[] rawData;
     GEntityData ed;
     boolean sendEntityData = false;
     BaseTarget target = EveryoneTarget.INSTANCE;


    public Packet0EntityUpdate() {
        ed = new GEntityData();
    }

    public Packet0EntityUpdate(byte[] rawData) {
        this.rawData = rawData;
    }

    public Packet0EntityUpdate(EntityData e) {
        this.ed = (GEntityData) e;
        sendEntityData = true;
    }

	@Override
	protected boolean doCacheIncoming() {
		return false;
	}

	@Override
	protected void internalDecode(ByteBuf buffer) {
		int zlibLength = buffer.readInt();
		rawData = new byte[zlibLength];
		buffer.readBytes(rawData);
        try {
            ByteBuf dataBuf = Unpooled.copiedBuffer(ZLibOperations.decompress(this.rawData));
            dataBuf = dataBuf.order(ByteOrder.LITTLE_ENDIAN);
            ed.decode(dataBuf);
        } catch (Exception e) {
            if(e instanceof IndexOutOfBoundsException && !Glydar.ignorePacketErrors) {
                Glydar.getServer().getLogger().severe("*************************CRITICAL ERROR*************************");
                e.printStackTrace();
                Glydar.getServer().getLogger().severe("Glydar has encountered a critical error during an ID0 packet decode, and will now shutdown.");
                Glydar.getServer().getLogger().severe("Please report this error to the developers. Attach the above stack trace and the id0err.dmp file in your Glydar root directory.");
                Random lotto = new Random();
                lotto.setSeed(System.currentTimeMillis());
                Glydar.getServer().getLogger().severe("Here is your magical error lotto number of the moment: <"+lotto.nextInt()+"> Please attach this to your bug report.");
                Glydar.getServer().getLogger().severe("****************************************************************");
                Glydar.getServer().getLogger().severe("NOTE: If you want Glydar to continue regardless, run Glydar with -ignorepacketerrors on the command line.");
                Glydar.getServer().getLogger().severe("****************************************************************");
                File dumpfile = new File("id0err.dmp");
                if(dumpfile.exists())
                    dumpfile.delete();

                try {
                    dumpfile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(dumpfile);
                    fos.write(ZLibOperations.decompress(rawData));
                    fos.close();
                } catch (Exception ex) { Glydar.getServer().getLogger().severe("Critical error encountered writing logfile dump. Boy, do you have bad luck."); }

                Glydar.shutdown();
            } else if(Glydar.ignorePacketErrors) {
                //For safety
                this.ed = null;
                this.sendEntityData = false;
            }

        }
    }

    @Override
    public void receivedFrom(GPlayer ply) {
        if(!ply.joined) {
            ply.setEntityData(this.ed);
            Glydar.getServer().getLogger().info("Player " + ply.getEntityData().getName() + " joined with entity ID " + ply.getEntityData().getId() + "! (Internal ID " + ply.entityID + ")");
            //TODO Send all current entity data and NOT just existing players
            for (GPlayer p : Glydar.getServer().getConnectedPlayers()) {
                if(p.entityID == ply.entityID) {
                    Glydar.getServer().getLogger().warning("I found myself! o.o");
                    continue;
                }
                ply.sendPacket(new Packet0EntityUpdate(p.getEntityData()));
            }
            ply.playerJoined();
        }
        manageEvents(ply);
        ((GEntityData) ply.getEntityData()).updateFrom(this.ed);
		this.sendTo(target);
    }

    @Override
    protected void internalEncode(ByteBuf buf) {
        if(!sendEntityData) {
            buf.writeInt(rawData.length);
            buf.writeBytes(rawData);
        } else {
            ByteBuf buf2 = Unpooled.buffer();
            buf2 = buf2.order(ByteOrder.LITTLE_ENDIAN);
            ed.encode(buf2);
            byte[] compressedData = null;
            try {
                compressedData = ZLibOperations.compress(buf2.array());
                System.out.println("Sending custom ED. Length: "+buf2.array().length+" compressed: "+compressedData.length);
            } catch (Exception e) { e.printStackTrace(); }
            if(compressedData != null) {
                buf.writeInt(compressedData.length);
                buf.writeBytes(compressedData);
                //new Packet2UpdateFinished().sendToAll();
            } else {
                Glydar.getServer().getLogger().warning("Compressed data is null, I'm just writing the raw data back!");
                buf.writeInt(rawData.length);
                buf.writeBytes(rawData);
           }
        }
    }
    
    @SuppressWarnings("restriction")
	public void manageEvents(GPlayer ply){
    	EntityUpdateEvent eue = (EntityUpdateEvent) EventManager.callEvent(new EntityUpdateEvent(ply, ed));
    	ed = (GEntityData) eue.getEntityData();
    	target = eue.getRecievers();
    	
    	BitArray bitArray = new BitArray(8*ed.getBitmask().length, Bitops.flipBits(ed.getBitmask()));
    	if (bitArray.get(0)){
    		//Move
    		EntityUpdateEvent eme = (EntityUpdateEvent) EventManager.callEvent(new EntityMoveEvent(ply, ed));
        	ed = (GEntityData) eme.getEntityData();
        	target = eue.getRecievers();
    	}
    	if (bitArray.get(27)){
    		//Health
    		EntityUpdateEvent ehe = (EntityUpdateEvent) EventManager.callEvent(new EntityHealthEvent(ply, ed));
        	ed = (GEntityData) ehe.getEntityData();
        	target = eue.getRecievers();
    	}
    }
}
