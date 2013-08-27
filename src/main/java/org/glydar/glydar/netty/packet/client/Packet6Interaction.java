package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.data.GItem;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 6)
public class Packet6Interaction extends CubeWorldPacket {

	GItem item;
	int chunkX, chunkY;
	int itemIndex; //Index of item in ChunkItems
	long something4; //uint
	byte interactType; //TODO ENUM
	byte something6;
	int something7; //ushort

	public Packet6Interaction() {
		item = new GItem();
	}

	@Override
	protected void internalDecode(ByteBuf buf) {
		item.decode(buf);
		chunkX = buf.readInt();
		chunkY = buf.readInt();
		itemIndex = buf.readInt();
		something4 = buf.readUnsignedInt();
		interactType = buf.readByte();
		something6 = buf.readByte();
		something7 = buf.readUnsignedShort();

	}

	@Override
	protected void internalEncode(ByteBuf buf) {
		item.encode(buf);
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
