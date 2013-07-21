package net.cwserver.netty.packet.server;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 10)
public class Packet10SendChat extends CubeWorldPacket {

    long sender;
    String message;

    public Packet10SendChat(String message, long sender) {
        this.sender = sender;
        this.message = message;

        System.out.println("(Chat) Entity ID "+sender+": "+message);
    }

    @Override
    public void encode(ByteBuf buf) {
        byte[] msgBuf = message.getBytes(Charsets.UTF_16LE);
        buf.writeLong(sender);
        //TODO Force string encoding.
        buf.writeInt(msgBuf.length / 2);
        buf.writeBytes(msgBuf);
    }
}
