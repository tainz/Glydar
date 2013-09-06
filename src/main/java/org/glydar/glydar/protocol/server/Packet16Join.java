package org.glydar.glydar.protocol.server;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

import io.netty.buffer.ByteBuf;

@PacketType(id = 16)
public class Packet16Join extends Packet {

	private long entId;
	private byte[] connInfo;

	public Packet16Join(long entId) {
		this.entId = entId;
		this.connInfo = new byte[4456];
	}

	@Override
	protected void internalEncode(ByteBuf buf) {
		buf.writeInt(0);
		buf.writeLong(entId);
		buf.writeBytes(connInfo);
	}
}
