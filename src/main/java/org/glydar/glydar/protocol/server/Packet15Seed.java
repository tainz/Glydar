package org.glydar.glydar.protocol.server;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet15Seed extends Packet {

	private int seed;

	public Packet15Seed(int seed) {
		this.seed = seed;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.SEED;
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(seed);
	}
}
