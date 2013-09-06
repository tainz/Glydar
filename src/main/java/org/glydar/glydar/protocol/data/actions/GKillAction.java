package org.glydar.glydar.protocol.data.actions;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.data.BaseData;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

}
