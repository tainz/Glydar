package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.models.Player;
import net.cwserver.models.ServerEntity;
import net.cwserver.netty.packet.CubeWorldPacket;
import net.cwserver.netty.packet.server.Packet15Seed;
import net.cwserver.netty.packet.server.Packet16Join;
import net.cwserver.netty.packet.shared.Packet10Chat;

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
