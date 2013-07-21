package net.cwserver.netty.packet;

import io.netty.buffer.ByteBuf;
import net.cwserver.models.BaseTarget;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.shared.Packet0EntityUpdate;

public abstract class CubeWorldPacket {
	public @interface Packet { int id(); boolean variableLength() default false; }

	public void decode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be decoded");
	}

	public void receivedFrom(Player ply) {
		throw new IllegalAccessError("Packet cannot be received");
	}

	public void encode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be encoded");
	}

	public void sendTo(Player ply) {
		throw new IllegalAccessError("Packet cannot be sent");
	}

	public void sendTo(BaseTarget target) {
		for(Player ply : target.getPlayers()) {
			sendTo(ply);
		}
	}

	public static CubeWorldPacket getByID(int id) {
		return new Packet0EntityUpdate();
	}
}
