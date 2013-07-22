package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 7)
public class Packet7HitNPC extends CubeWorldPacket {
	int _U1;
	int _U2;
	int _U3;
	int _U4;
	byte _U5;
	int _U6; //THREE BYTES (medium!)
	int _U7;
	int _U8;
	int _U9;
	int _U10;
	int _U11;
	int _U12;
	int _U13;
	int _U14;
	int _U15;
	int _U16;
	int _U17;
	int _U18;
	byte _U19;
	byte _U20;
	byte _U21;

	@Override
	protected void internalDecode(ByteBuf buf) {
		_U1 = buf.readInt();
		_U2 = buf.readInt();
		_U3 = buf.readInt();
		_U4 = buf.readInt();
		_U5 = buf.readByte();
		_U6 = buf.readMedium();
		_U7 = buf.readInt();
		_U8 = buf.readInt();
		_U9 = buf.readInt();
		_U10 = buf.readInt();
		_U11 = buf.readInt();
		_U12 = buf.readInt();
		_U13 = buf.readInt();
		_U14 = buf.readInt();
		_U15 = buf.readInt();
		_U16 = buf.readInt();
		_U17 = buf.readInt();
		_U18 = buf.readInt();
		_U19 = buf.readByte();
		_U20 = buf.readByte();
		_U21 = buf.readByte();
	}
}
