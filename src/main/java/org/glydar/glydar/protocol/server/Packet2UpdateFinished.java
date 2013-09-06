package org.glydar.glydar.protocol.server;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 2, noData = true)
public class Packet2UpdateFinished extends Packet {

	public Packet2UpdateFinished() {
	}
}
