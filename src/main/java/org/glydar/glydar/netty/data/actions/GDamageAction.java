package org.glydar.glydar.netty.data.actions;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.netty.data.BaseData;

public class GDamageAction implements BaseData {

	long id;
	long targetId;
	float damage;
	
	@Override
	public void decode(ByteBuf buf) {
		id = buf.readLong();
		targetId = buf.readLong();
		damage = buf.readFloat();
		buf.readBytes(4); //Maybe not padding

	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(id);
		buf.writeLong(targetId);
		buf.writeFloat(damage);
		buf.writeBytes(new byte[4]);
	}

}
