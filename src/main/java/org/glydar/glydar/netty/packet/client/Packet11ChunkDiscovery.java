package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 11)
public class Packet11ChunkDiscovery extends CubeWorldPacket {
	int chunkX;
	int chunkZ;

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
