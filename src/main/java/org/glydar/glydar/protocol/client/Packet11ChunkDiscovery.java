package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet11ChunkDiscovery extends Packet {

	@SuppressWarnings("unused")
	private final int chunkX;
	@SuppressWarnings("unused")
	private final int chunkZ;

	public Packet11ChunkDiscovery(ByteBuf buf) {
		chunkX = buf.readInt();
		chunkZ = buf.readInt();
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.CHUNK_DISCOVERY;
	}

	@Override
	public void receivedFrom(GPlayer ply) {
	}
}
