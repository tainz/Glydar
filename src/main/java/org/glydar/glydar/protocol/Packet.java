package org.glydar.glydar.protocol;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.paraglydar.models.BaseTarget;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.models.WorldTarget;

public abstract class Packet {

	public abstract PacketType getPacketType();

	public void encode(ByteBuf buf) {
		throw new UnsupportedOperationException("Packet cannot be encoded");
	}

	public void receivedFrom(GPlayer ply) {
		throw new UnsupportedOperationException("Packet cannot be received");
	}

	public void sendTo(GPlayer ply) {
		ply.getChannelContext().writeAndFlush(this);
	}

	public void sendTo(BaseTarget target) {
		for (Player ply : target.getPlayers()) {
			sendTo((GPlayer) ply);
		}
	}

	public void sendToWorld(World w) {
		sendTo(new WorldTarget(w));
	}
}
