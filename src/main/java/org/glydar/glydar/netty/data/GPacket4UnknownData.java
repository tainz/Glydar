package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class GPacket4UnknownData implements BaseData {
	long u1;
	long u2;
	long u3;
	byte u4;
	byte u5;
	byte u6;
	byte u7;
	long u8;

	@Override
	public void decode(ByteBuf buf) {
		u1 = buf.readUnsignedInt();
		u2 = buf.readUnsignedInt();
		u3 = buf.readUnsignedInt();
		u4 = buf.readByte();
		u5 = buf.readByte();
		u6 = buf.readByte();
		u7 = buf.readByte();
		u8 = buf.readUnsignedInt();
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt((int) u1);
		buf.writeInt((int) u2);
		buf.writeInt((int) u3);
		buf.writeByte(u4);
		buf.writeByte(u5);
		buf.writeByte(u6);
		buf.writeByte(u7);
		buf.writeInt((int) u8);
	}

}
