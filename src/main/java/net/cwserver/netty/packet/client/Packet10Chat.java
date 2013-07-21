package net.cwserver.netty.packet.client;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.CubeWorldPacket;
import net.cwserver.netty.packet.server.Packet10SendChat;

import java.io.UnsupportedEncodingException;

@CubeWorldPacket.Packet(id = 10, variableLength = true)
public class Packet10Chat extends CubeWorldPacket {
    int length;
    byte[] messageBytes;
    String message;

    @Override
    public void decode(ByteBuf buf) {
        length = buf.readInt();
        messageBytes = new byte[length * 2];
        buf.readBytes(messageBytes);
        message = new String(messageBytes, Charsets.UTF_16LE);
    }

    @Override
    public void receivedFrom(Player ply) {
        ply.getChannelContext().write(new Packet10SendChat(message, ply.entityID));
    }
}
