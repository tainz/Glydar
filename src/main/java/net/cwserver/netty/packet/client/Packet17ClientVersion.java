package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.CubeWorldPacket;
import net.cwserver.netty.packet.server.Packet17WrongVersion;

@CubeWorldPacket.Packet(id = 17)
public class Packet17ClientVersion extends CubeWorldPacket {
    int version;

    @Override
    public void decode(ByteBuf buf) {
        version = buf.readInt();
    }

    @Override
    public void receivedFrom(Player ply) {
        System.out.print("Client from "+ply.getChannelContext().channel().remoteAddress()+" says hi! Version "+version);

    }
}
