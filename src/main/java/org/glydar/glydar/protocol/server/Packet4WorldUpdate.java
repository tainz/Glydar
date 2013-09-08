package org.glydar.glydar.protocol.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.protocol.data.GWorldUpdateData;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;
import org.glydar.glydar.util.ZLibOperations;

import java.nio.ByteOrder;

public class Packet4WorldUpdate extends Packet {

	private GWorldUpdateData sud;

	public Packet4WorldUpdate() {
		this.sud = new GWorldUpdateData();
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.WORLD_UPDATE;
	}

	@Override
	public void encode(ByteBuf buf) {
		ByteBuf buf2 = Unpooled.buffer();
		buf2 = buf2.order(ByteOrder.LITTLE_ENDIAN);
		sud.encode(buf2);
		byte[] compressedData = null;
		try {
			compressedData = ZLibOperations.compress(buf2.array());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (compressedData != null) {
			buf.writeInt(compressedData.length);
			buf.writeBytes(compressedData);
		} else {
			Glydar.getServer().getLogger().severe("World update is null! o.o");
		}
	}

	public GWorldUpdateData getData() {
		return sud;
	}
}
