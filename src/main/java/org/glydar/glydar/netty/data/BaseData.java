package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public interface BaseData {
	public void decode(ByteBuf buf);

	public void encode(ByteBuf buf);
}
