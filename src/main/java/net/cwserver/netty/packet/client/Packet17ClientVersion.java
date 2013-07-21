package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 17)
public class Packet17ClientVersion extends CubeWorldPacket {
    int version;

    @Override
    public void decode(ByteBuf buf) {
        version = buf.readInt();
    }
}
