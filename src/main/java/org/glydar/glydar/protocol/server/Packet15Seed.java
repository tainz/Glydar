package org.glydar.glydar.protocol.server;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 15)
public class Packet15Seed extends Packet {

	private int seed;

	public Packet15Seed(int seed) {
		this.seed = seed;
	}

	@Override
	protected void internalEncode(ByteBuf buf) {
		buf.writeInt(seed);
	}
}
