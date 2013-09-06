package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

@PacketType(id = 13)
public class Packet13MissionData extends Packet {

	private int sectorX;
	private int sectorY;
	private long something3; // uint
	private long something4; // uint
	private long something5; // uint
	private long monsterID; // uint
	private long questLevel; // uint
	private short something8; // ubyte
	private short something9; // ubyte
	private float something10;
	private float something11;
	private long chunkX; // uint
	private long chunkY; // uint

	@Override
	public void decode(ByteBuf buf) {
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

	@Override
	public void encode(ByteBuf buf) {
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
