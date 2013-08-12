package org.glydar.glydar.netty.packet.server;

import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 2, noData = true)
public class Packet2UpdateFinished extends CubeWorldPacket {

	public Packet2UpdateFinished() {}
}
