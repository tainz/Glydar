package org.glydar.glydar.netty.packet.shared;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.server.Packet15Seed;
import org.glydar.glydar.netty.packet.server.Packet16Join;

@CubeWorldPacket.Packet(id = 17)
public class Packet17VersionExchange extends CubeWorldPacket {
	int version;

	public Packet17VersionExchange() {

	}

	public Packet17VersionExchange(int serverVersion) {
		this.version = serverVersion;
	}

	@Override
	protected void internalDecode(ByteBuf buf) {
		version = buf.readInt();
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		Glydar.getServer().getLogger().info("Client from " + ply.getChannelContext().channel().remoteAddress() + " says hi! Version " + version + " ent id " + ply.entityID);

		ply.sendPacket(new Packet16Join(ply.entityID));
		ply.sendPacket(new Packet15Seed(ply.getWorld().getSeed()));
		ply.sendMessageToPlayer("Server powered by Glydar 0.0.1-SNAPSHOT");
	}

	@Override
	protected void internalEncode(ByteBuf buf) {
		buf.writeInt(version);
	}
}
