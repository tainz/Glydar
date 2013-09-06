package org.glydar.glydar.protocol.server;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet2UpdateFinished extends Packet {

	@Override
	public PacketType getPacketType() {
		return PacketType.UPDATE_FINISHED;
	}

	@Override
	public void encode(ByteBuf buf) {
	}
}
