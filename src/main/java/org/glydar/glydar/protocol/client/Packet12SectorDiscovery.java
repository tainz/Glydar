package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet12SectorDiscovery extends Packet {

	@SuppressWarnings("unused")
	private final int sectorX;
	@SuppressWarnings("unused")
	private final int sectorZ;

	public Packet12SectorDiscovery(ByteBuf buf) {
		this.sectorX = buf.readInt();
		this.sectorZ = buf.readInt();
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.SECTOR_DISCOVERY;
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		//TODO: Stuff!
	}
}
