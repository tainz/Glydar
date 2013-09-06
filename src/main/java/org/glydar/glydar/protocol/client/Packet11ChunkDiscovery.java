package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 11)
public class Packet11ChunkDiscovery extends Packet {

	@SuppressWarnings("unused")
	private int chunkX;
	@SuppressWarnings("unused")
	private int chunkZ;

	@Override
	protected void internalDecode(ByteBuf buf) {
		chunkX = buf.readInt();
		chunkZ = buf.readInt();
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		//TODO: Stuff!
	}
}
