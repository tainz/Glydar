package org.glydar.glydar.netty.packet.server;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 15)
public class Packet15Seed extends CubeWorldPacket {
	int seed;

	public Packet15Seed(int seed) {
		this.seed = seed;
	}

	@Override
	protected void internalEncode(ByteBuf buf) {
		buf.writeInt(seed);
	}
}
