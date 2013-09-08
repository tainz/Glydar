package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet8Stealth extends Packet {

	@SuppressWarnings("unused")
	private final long id;
	private final byte[] unknowndata;

	public Packet8Stealth(ByteBuf buf) {
		id = buf.readLong();
		this.unknowndata = new byte[32];
		buf.readBytes(unknowndata);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.STEALTH;
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		Glydar.getServer().getLogger().info("Packet 8!");
	}
}
