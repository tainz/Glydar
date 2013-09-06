package org.glydar.glydar.protocol.shared;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;
import org.glydar.glydar.protocol.server.Packet15Seed;
import org.glydar.glydar.protocol.server.Packet16Join;
import org.glydar.glydar.protocol.server.Packet18ServerFull;

@PacketType(id = 17)
public class Packet17VersionExchange extends Packet {

	private int version;

	public Packet17VersionExchange(int serverVersion) {
		this.version = serverVersion;
	}

	@Override
	public void decode(ByteBuf buf) {
		version = buf.readInt();
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(version);
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		if (Glydar.getServer().getConnectedPlayers().size() > Glydar.getServer().getMaxPlayers()){
			ply.sendPacket(new Packet18ServerFull());
			return;
		}
		ply.sendPacket(new Packet16Join(ply.entityID));
		ply.sendPacket(new Packet15Seed(ply.getWorld().getSeed()));
		ply.sendMessageToPlayer("Server powered by Glydar 0.0.1-SNAPSHOT");
	}
}
