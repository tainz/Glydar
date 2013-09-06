package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 13)
public class Packet13MissionData extends Packet {
	int sectorX;
	int sectorY;
	long something3; // uint
	long something4; // uint
	long something5; // uint
	long monsterID; // uint
	long questLevel; // uint
	short something8; // ubyte
	short something9; // ubyte
	float something10;
	float something11;
	long chunkX; // uint
	long chunkY; // uint

	@Override
	protected void internalDecode(ByteBuf buf) {
		sectorX = buf.readInt() / 8;
		sectorY = buf.readInt() / 8;
		something3 = buf.readUnsignedInt();
		something4 = buf.readUnsignedInt();
		something5 = buf.readUnsignedInt();
		monsterID = buf.readUnsignedInt();
		questLevel = buf.readUnsignedInt();
		something8 = buf.readUnsignedByte();
		something9 = buf.readUnsignedByte();
		buf.readBytes(2);
		something10 = buf.readFloat();
		something11 = buf.readFloat();
		chunkX = buf.readUnsignedInt();
		chunkY = buf.readUnsignedInt();
	}

	protected void internalEncode(ByteBuf buf) {
		buf.writeInt(sectorX * 8);
		buf.writeInt(sectorY * 8);
		buf.writeLong(something3);
		buf.writeLong(something4);
		buf.writeLong(something5);
		buf.writeLong(monsterID);
		buf.writeLong(questLevel);
		buf.writeShort(something8);
		buf.writeShort(something9);
		buf.writeBytes(new byte[2]);
		buf.writeFloat(something10);
		buf.writeFloat(something11);
		buf.writeLong(chunkX);
		buf.writeLong(chunkY);
	}
}
