package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet8Stealth extends Packet {

	private byte[] unknowndata;

	public Packet8Stealth(ByteBuf buf) {
		buf.readBytes(unknowndata, 0, 40);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.STEALTH;
	}
}
