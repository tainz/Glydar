package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.data.ItemUpgrades;
import net.cwserver.netty.packet.CubeWorldPacket;

public class Packet6Interaction extends CubeWorldPacket {
	byte type;
	byte subType;
	short _U1;
	int modifier;
	int _U2;
	short rarity; // UNSINGED BYTE
	short material; // UNSINGED BYTE
	int itemFlags; // UNSIGNED SHORT
	short level;
	ItemUpgrades itemUpgrades;

	@Override
	public void decode(ByteBuf buf) {
		type = buf.readByte();
		subType = buf.readByte();
		_U1 = buf.readShort();
		modifier = buf.readInt();
		_U2 = buf.readInt();
		rarity = buf.readUnsignedByte();
		material = buf.readUnsignedByte();
		itemFlags = buf.readUnsignedShort();
		level = buf.readShort();
		itemUpgrades = new ItemUpgrades();
		itemUpgrades.decode(buf);
	}
}
