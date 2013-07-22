package net.cwserver.netty.packet.server;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 17)
public class Packet17WrongVersion extends CubeWorldPacket {
    int serverVersion;

    public Packet17WrongVersion(int serverVersion) {
        this.serverVersion = serverVersion;
    }

    @Override
    protected void internalEncode(ByteBuf buf) {
        buf.writeInt(serverVersion);
    }
}
