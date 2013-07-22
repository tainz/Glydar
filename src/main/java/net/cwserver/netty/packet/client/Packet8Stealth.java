package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 8)
public class Packet8Stealth extends CubeWorldPacket {

    byte[] unknowndata;
    @Override
    protected void internalDecode(ByteBuf buf) {
        buf.readBytes(unknowndata, 0, 40);
    }
}
