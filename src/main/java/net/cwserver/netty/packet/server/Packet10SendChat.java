package net.cwserver.netty.packet.server;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 10)
public class Packet10SendChat extends CubeWorldPacket {

    int sender;
    String message;

    public Packet10SendChat(String message, int sender) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(sender);
        //TODO Force string encoding.
        buf.writeInt(message.getBytes().length / 2);
        buf.writeBytes(message.getBytes());
    }
}
