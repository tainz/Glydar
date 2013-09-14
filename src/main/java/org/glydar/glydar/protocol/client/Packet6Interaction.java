package org.glydar.glydar.protocol.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;
import org.glydar.glydar.protocol.data.DataCodec;
import org.glydar.paraglydar.data.Item;

public class Packet6Interaction extends Packet {

	private final Item item;
	private final int chunkX;
	private final int chunkY;
	private final int itemIndex; //Index of item in ChunkItems
	private final long something4; //uint
	private final byte interactType; //TODO ENUM
	private final byte something6;
	private final int something7; //ushort

	public Packet6Interaction(ByteBuf buf) {
		item = DataCodec.readItem(buf);
		chunkX = buf.readInt();
		chunkY = buf.readInt();
		itemIndex = buf.readInt();
		something4 = buf.readUnsignedInt();
		interactType = buf.readByte();
		something6 = buf.readByte();
		something7 = buf.readUnsignedShort();
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.INTERACTION;
	}

	@Override
	public void encode(ByteBuf buf) {
		DataCodec.writeItem(buf, item);
		buf.writeInt(chunkX);
		buf.writeInt(chunkY);
		buf.writeInt(itemIndex);
		buf.writeInt((int) something4);
		buf.writeByte(interactType);
		buf.writeByte(something6);
		buf.writeShort(something7);
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		Glydar.getServer().getLogger().info("Packet 6 recieved!");
	}
}
