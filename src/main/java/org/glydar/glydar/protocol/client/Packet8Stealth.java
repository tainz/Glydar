package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 8)
public class Packet8Stealth extends Packet {

	private byte[] unknowndata;

	@Override
	public void decode(ByteBuf buf) {
		buf.readBytes(unknowndata, 0, 40);
	}
}
