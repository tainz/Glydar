package net.cwserver.netty.data;

import io.netty.buffer.ByteBuf;

public class Item implements BaseData{
    byte type, subtype;
    long modifier; //Uint
    long minusModifier; //Uint
    byte rarity, material, flags;
    int level; //ushort
    ItemUpgrades upgrades;
    //int upgradeCount; //unsigned

    @Override
    public void decode(ByteBuf buf) {
        type = buf.readByte();
        subtype = buf.readByte();
        buf.readBytes(2); //Skip
        modifier = buf.readUnsignedInt();
        minusModifier = buf.readUnsignedInt();
        rarity = buf.readByte();
        material = buf.readByte();
        flags = buf.readByte();
        buf.readByte();
        level = buf.readUnsignedShort();
        buf.readBytes(2); //Skip
        upgrades = new ItemUpgrades();
        upgrades.decode(buf);

    }
}
