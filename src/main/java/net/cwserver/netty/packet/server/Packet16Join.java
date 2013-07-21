package net.cwserver.netty.packet.server;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 16)
public class Packet16Join extends CubeWorldPacket {

    int entId;
    byte[] connInfo;

    public Packet16Join(int entId) {
        this.entId = entId;
        this.connInfo = new byte[4456];
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(entId);
        buf.writeBytes(connInfo);
    }
}
