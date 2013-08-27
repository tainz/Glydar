package org.glydar.glydar.netty.packet.server;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 16)
public class Packet16Join extends CubeWorldPacket {

	long entId;
	byte[] connInfo;

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
