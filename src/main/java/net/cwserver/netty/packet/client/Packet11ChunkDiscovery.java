package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;


@CubeWorldPacket.Packet(id = 11)
public class Packet11ChunkDiscovery extends CubeWorldPacket {
    int chunkX;
    int chunkZ;

    @Override
    public void decode(ByteBuf buf) {
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
    }
}
