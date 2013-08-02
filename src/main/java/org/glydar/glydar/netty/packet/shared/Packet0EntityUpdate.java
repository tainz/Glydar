package org.glydar.glydar.netty.packet.shared;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.netty.data.EntityData;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.util.ZLibOperations;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteOrder;
import java.util.Random;

@CubeWorldPacket.Packet(id = 0, variableLength = true)
public class Packet0EntityUpdate extends CubeWorldPacket {
	 byte[] rawData;
     EntityData ed;
     boolean sendEntityData = false;


    public Packet0EntityUpdate() {
        ed = new EntityData();
    }

    public Packet0EntityUpdate(byte[] rawData) {
        this.rawData = rawData;
    }

    public Packet0EntityUpdate(EntityData e) {
        this.ed = e;
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
            if(e instanceof IndexOutOfBoundsException) {
                Glydar.getServer().getLogger().severe("*************************CRITICAL ERROR*************************");
                e.printStackTrace();
                Glydar.getServer().getLogger().severe("Glydar has encountered a critical error during an ID0 packet decode, and will now shutdown.");
                Glydar.getServer().getLogger().severe("Please report this error to the developers. Attach the above stack trace and the id0err.dmp file in your Glydar root directory.");
                Random lotto = new Random();
                lotto.setSeed(System.currentTimeMillis());
                Glydar.getServer().getLogger().severe("Here is your magical error lotto number of the moment: <"+lotto.nextInt()+"> Please attach this to your bug report.");
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
            }

        }
    }

    @Override
    public void receivedFrom(Player ply) {
        if(!ply.joined) {
            ply.data = this.ed;
            Glydar.getServer().getLogger().info("Player " + ply.data.name + " joined with entity ID " + ply.data.id + "! (Internal ID " + ply.entityID + ")");
            //TODO Send all current entity data and NOT just existing players
            for (Player p : Player.getConnectedPlayers()) {
                if(p.entityID == ply.entityID) {
                    Glydar.getServer().getLogger().warning("I found myself! o.o");
                    continue;
                }
                ply.sendPacket(new Packet0EntityUpdate(p.data));
            }
            ply.playerJoined();
        }
        ply.data.updateFrom(this.ed);
		this.sendToAll();
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
            } else {
                Glydar.getServer().getLogger().warning("Compressed data is null, I'm just writing the raw data back!");
                buf.writeInt(rawData.length);
                buf.writeBytes(rawData);
           }
        }
    }
}
