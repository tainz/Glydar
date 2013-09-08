package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet8Stealth extends Packet {

	private byte[] unknowndata;
	private long id, l2, l3, l4,l5;

	public Packet8Stealth(ByteBuf buf) {
		/*this.unknowndata = new byte[40];
		buf.readBytes(unknowndata);*/
		id = buf.readLong();
		//Glydar.getServer().getLogger().info("id: " + id);
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
