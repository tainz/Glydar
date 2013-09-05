package org.glydar.glydar.netty.data;

import static org.glydar.glydar.util.VectorBuf.readLongVector3;
import static org.glydar.glydar.util.VectorBuf.writeLongVector3;

import org.glydar.paraglydar.geom.LongVector3;

import io.netty.buffer.ByteBuf;

public class GChunkItemData implements BaseData {

	GItem item;
	LongVector3 position;
	float rotation;
	float scale;
	byte unknown1;
	long dropTime;
	long unknown2;
	int unknown3;

	public GChunkItemData() {
		item = new GItem();
		position = new LongVector3();
	}

	@Override
	public void decode(ByteBuf buf) {
		item.decode(buf);
		position = readLongVector3(buf);
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
		writeLongVector3(buf, position);
		buf.writeFloat(rotation);
		buf.writeFloat(scale);
		buf.writeByte(unknown1);
		buf.writeBytes(new byte[3]);
		buf.writeInt((int) dropTime);
		buf.writeInt((int) unknown2);
		buf.writeInt(unknown3);
	}
}
