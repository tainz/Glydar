package org.glydar.glydar.netty.packet.server;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteOrder;
import java.util.Random;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.data.GServerUpdateData;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.util.ZLibOperations;

@CubeWorldPacket.Packet(id = 4)
public class Packet4ServerUpdate extends CubeWorldPacket {
	public GServerUpdateData sud;
	byte[] rawData;
	
	//Not sure if this (decoding) is needed!?
	@Override
	protected void internalDecode(ByteBuf buf) {
		int zlibLength = buf.readInt();
		rawData = new byte[zlibLength];
		buf.readBytes(rawData);
        try {
            ByteBuf dataBuf = Unpooled.copiedBuffer(ZLibOperations.decompress(this.rawData));
            dataBuf = dataBuf.order(ByteOrder.LITTLE_ENDIAN);
            sud.decode(dataBuf);
        } catch (Exception e) {
            if(e instanceof IndexOutOfBoundsException && !Glydar.ignorePacketErrors) {
                Glydar.getServer().getLogger().severe("*************************CRITICAL ERROR*************************");
                e.printStackTrace();
                Glydar.getServer().getLogger().severe("Glydar has encountered a critical error during an ID4 packet decode, and will now shutdown.");
                Glydar.getServer().getLogger().severe("Please report this error to the developers. Attach the above stack trace and the id4err.dmp file in your Glydar root directory.");
                Random lotto = new Random();
                lotto.setSeed(System.currentTimeMillis());
                Glydar.getServer().getLogger().severe("Here is your magical error lotto number of the moment: <"+lotto.nextInt()+"> Please attach this to your bug report.");
                Glydar.getServer().getLogger().severe("****************************************************************");
                Glydar.getServer().getLogger().severe("NOTE: If you want Glydar to continue regardless, run Glydar with -ignorepacketerrors on the command line.");
                Glydar.getServer().getLogger().severe("****************************************************************");
                File dumpfile = new File("id4err.dmp");
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
	protected void internalEncode(ByteBuf buf) {
		ByteBuf buf2 = Unpooled.buffer();
        buf2 = buf2.order(ByteOrder.LITTLE_ENDIAN);
        sud.encode(buf2);
        byte[] compressedData = null;
        try {
            compressedData = ZLibOperations.compress(buf2.array());
            System.out.println("Sending custom ED. Length: "+buf2.array().length+" compressed: "+compressedData.length);
        } catch (Exception e) { e.printStackTrace(); }
        if(compressedData != null) {
            buf.writeInt(compressedData.length);
            buf.writeBytes(compressedData);
        } else {
            Glydar.getServer().getLogger().severe("Server Update packet is nulll.., something fricked up :(");
       }
	}
	
}
