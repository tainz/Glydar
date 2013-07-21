package net.cwserver.netty.packet.server;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 15)
public class Packet15Seed extends CubeWorldPacket {
    int seed;

    public Packet15Seed(int seed) {
        this.seed = seed;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(seed);
    }
}
