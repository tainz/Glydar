package org.glydar.paraglydar.netty.data;

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

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(type);
        buf.writeByte(subtype);
        buf.writeBytes(new byte[2]);
        buf.writeInt((int)modifier);
        buf.writeInt((int)minusModifier);
        buf.writeByte(rarity);
        buf.writeByte(material);
        buf.writeByte(flags);
        buf.writeBytes(new byte[1]);
        buf.writeShort(level);
        buf.readBytes(new byte[2]);
        upgrades.encode(buf);

    }
}
