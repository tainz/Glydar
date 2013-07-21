package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.data.Item;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 6)
public class Packet6Interaction extends CubeWorldPacket {

    Item item;
    int chunkX, chunkY;
    int itemIndex; //Index of item in ChunkItems
    long something4; //uint
    byte interactType; //TODO ENUM
    byte something6;
    int something7; //ushort

	@Override
	public void decode(ByteBuf buf) {
		item = new Item();
        item.decode(buf);
        chunkX = buf.readInt();
        chunkY = buf.readInt();
        itemIndex = buf.readInt();
        something4 = buf.readUnsignedInt();
        interactType = buf.readByte();
        something6 = buf.readByte();
        something7 = buf.readUnsignedShort();

	}
}
