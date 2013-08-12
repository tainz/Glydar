package org.glydar.glydar.netty.data.actions;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.netty.data.BaseData;

public class GKillAction implements BaseData {

	long id;
	long targetId;
	int xp;
	
	@Override
	public void decode(ByteBuf buf) {
		id = buf.readLong();
		targetId = buf.readLong();
		buf.readBytes(4); //Maybe not padding
		xp = buf.readInt();
		
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(id);
		buf.writeLong(targetId);
		buf.writeBytes(new byte[4]);
		buf.writeInt(xp);
		
	}

}
