package org.glydar.glydar.protocol.data.actions;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.data.BaseData;
import org.glydar.glydar.protocol.data.DataCodec;
import org.glydar.paraglydar.data.Item;

public class GPickupAction implements BaseData {

	long id;
	Item item;

	public GPickupAction() {
		item = new Item();
	}

	@Override
	public void decode(ByteBuf buf) {
		id = buf.readLong();
		item = DataCodec.readItem(buf);

	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(id);
		DataCodec.writeItem(buf, item);
	}

}
