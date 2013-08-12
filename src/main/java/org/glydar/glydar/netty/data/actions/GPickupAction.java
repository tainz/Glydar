package org.glydar.glydar.netty.data.actions;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.netty.data.BaseData;
import org.glydar.glydar.netty.data.GItem;

public class GPickupAction implements BaseData {

	long id;
	GItem item;
	
	@Override
	public void decode(ByteBuf buf) {
		id = buf.readLong();
		item = new GItem();
		item.decode(buf);
		
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(id);
		item.encode(buf);
	}

}
