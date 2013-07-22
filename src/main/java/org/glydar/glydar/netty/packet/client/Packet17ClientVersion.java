package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.models.ServerEntity;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.server.Packet15Seed;
import org.glydar.glydar.netty.packet.server.Packet16Join;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;

@CubeWorldPacket.Packet(id = 17)
public class Packet17ClientVersion extends CubeWorldPacket {
    int version;

    @Override
    protected void internalDecode(ByteBuf buf) {
        version = buf.readInt();
    }

    @Override
    public void receivedFrom(Player ply) {
        System.out.println("Client from " + ply.getChannelContext().channel().remoteAddress() + " says hi! Version " + version + " ent id "+ply.entityID);

        ply.sendPacket(new Packet16Join(ply.entityID));
        ply.sendPacket(new Packet15Seed(69));
        ply.sendPacket(new Packet10Chat("Welcome to FoxCube v0!", ServerEntity.INSTANCE));
    }
}
