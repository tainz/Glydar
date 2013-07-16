package net.cwserver.netty.data;

import io.netty.buffer.ByteBuf;

public class ItemUpgrade implements BaseData {
	byte xOffset;
	byte yOffset;
	byte zOffset;
	byte material;
	short level;
	short _U1;

	@Override
	public void decode(ByteBuf buf) {
		xOffset = buf.readByte();
		yOffset = buf.readByte();
		zOffset = buf.readByte();
		material = buf.readByte();
		level = buf.readShort();
		_U1 = buf.readShort();
	}
}
