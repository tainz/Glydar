package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

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
