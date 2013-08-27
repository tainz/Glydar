package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 12)
public class Packet12SectorDiscovery extends CubeWorldPacket {
	int sectorX;
	int sectorZ;

	@Override
	protected void internalDecode(ByteBuf buf) {
		sectorX = buf.readInt();
		sectorZ = buf.readInt();
	}
}
