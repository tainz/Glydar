package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class GChunkItemData implements BaseData {

	GItem item;
	GVector3 position;
	float rotation;
	float scale;
	byte unknown1;
	long dropTime;
	long unknown2;
	int unknown3;
	
	@Override
	public void decode(ByteBuf buf) {
		item = new GItem();
		item.decode(buf);
		position = new GVector3();
		position.decode(buf);
		rotation = buf.readFloat();
		scale = buf.readFloat();
		unknown1 = buf.readByte();
		buf.readBytes(3);
		dropTime = buf.readUnsignedInt();
		unknown2 = buf.readUnsignedInt();
		unknown3 = buf.readInt();
	}

	@Override
	public void encode(ByteBuf buf) {
		item.encode(buf);
		position.encode(buf);
		buf.writeFloat(rotation);
		buf.writeFloat(scale);
		buf.writeByte(unknown1);
		buf.writeBytes(new byte[3]);
		buf.writeInt((int) dropTime);
		buf.writeInt((int) unknown2);
		buf.writeInt(unknown3);
	}

}
