package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class GChunkItems implements BaseData {

	int chunkLocX;
	int chunkLocY;
	GChunkItemData[] items;

	@Override
	public void decode(ByteBuf buf) {
		chunkLocX = buf.readInt();
		chunkLocY = buf.readInt();

		int length = buf.readInt();
		for (int i = 0; i < length; i++) {
			items[i] = new GChunkItemData();
			items[i].decode(buf);
		}

	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(chunkLocX);
		buf.writeInt(chunkLocY);
		buf.writeInt(items.length);
		for (GChunkItemData i : items) {
			i.encode(buf);
		}

	}

}
