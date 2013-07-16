package net.cwserver.netty.packet;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.shared.Packet0EntityUpdate;

public abstract class CubeWorldPacket {
	public @interface Packet { int id(); boolean variableLength() default false; }

	public void decode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be decoded");
	}

	public void encode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be encoded");
	}

	public static CubeWorldPacket getByID(int id) {
		return new Packet0EntityUpdate();
	}
}
