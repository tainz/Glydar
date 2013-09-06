package org.glydar.glydar.protocol.data;

import io.netty.buffer.ByteBuf;

public interface BaseData {
	public void decode(ByteBuf buf);

	public void encode(ByteBuf buf);
}
