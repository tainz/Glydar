package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 12)
public class Packet12SectorDiscovery extends Packet {
	int sectorX;
	int sectorZ;

	@Override
	protected void internalDecode(ByteBuf buf) {
		sectorX = buf.readInt();
		sectorZ = buf.readInt();
	}
	
	@Override
	public void receivedFrom(GPlayer ply) {
		//TODO: Stuff!
	}
}
