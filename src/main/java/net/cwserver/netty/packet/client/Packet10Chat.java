package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

import java.io.UnsupportedEncodingException;

@CubeWorldPacket.Packet(id = 10, variableLength = true)
public class Packet10Chat extends CubeWorldPacket {
    int length;
    byte[] messageBytes;
    String message;

    @Override
    public void decode(ByteBuf buf) {
        length = buf.readInt();
        buf.readBytes(messageBytes, 0, length * 2);
        try {
            message = new String(messageBytes, "UTF-16LE");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
