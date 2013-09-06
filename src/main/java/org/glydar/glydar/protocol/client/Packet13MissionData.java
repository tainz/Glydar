package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

public class Packet13MissionData extends Packet {

	private final int sectorX;
	private final int sectorY;
	private final long something3; // uint
	private final long something4; // uint
	private final long something5; // uint
	private final long monsterID; // uint
	private final long questLevel; // uint
	private final short something8; // ubyte
	private final short something9; // ubyte
	private final float something10;
	private final float something11;
	private final long chunkX; // uint
	private final long chunkY; // uint

	public Packet13MissionData(ByteBuf buf) {
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
	public PacketType getPacketType() {
		return PacketType.MISSION_DATA;
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
