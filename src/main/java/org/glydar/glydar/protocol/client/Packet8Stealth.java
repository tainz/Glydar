package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet8Stealth extends Packet {

	private byte[] unknowndata;

	public Packet8Stealth(ByteBuf buf) {
		this.unknowndata = new byte[40];
		buf.readBytes(unknowndata);
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.STEALTH;
	}
}
