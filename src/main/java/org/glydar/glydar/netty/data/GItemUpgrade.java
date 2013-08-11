package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class ItemUpgrade implements BaseData {
	byte xOffset;
	byte yOffset;
	byte zOffset;
	byte material;
	long level;

	@Override
	public void decode(ByteBuf buf) {
		xOffset = buf.readByte();
		yOffset = buf.readByte();
		zOffset = buf.readByte();
		material = buf.readByte();
		level = buf.readUnsignedInt();
	}

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(xOffset);
        buf.writeByte(yOffset);
        buf.writeByte(zOffset);
        buf.writeByte(material);
        buf.writeInt((int)level);
    }

}
