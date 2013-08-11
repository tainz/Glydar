package org.glydar.glydar.netty.data;

import org.glydar.api.data.Item;
import org.glydar.api.data.ItemUpgrade;

import io.netty.buffer.ByteBuf;

public class GItem implements BaseData, Item {
    byte type, subtype;
    long modifier; //Uint
    long minusModifier; //Uint
    byte rarity, material, flags;
    int level; //ushort
    GItemUpgrade[] upgrades;
    long upgradeCount; //unsigned

    public GItem() {
        upgrades = new GItemUpgrade[32];
    }

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
        for (int i = 0; i < upgrades.length; ++i) {
            upgrades[i] = new GItemUpgrade();
            upgrades[i].decode(buf);
        }

        upgradeCount = buf.readUnsignedInt();
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
        buf.writeBytes(new byte[2]);
        for (int i = 0; i < upgrades.length; ++i) {
            upgrades[i].encode(buf);
        }
        buf.writeInt((int) upgradeCount);

    }

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getSubtype() {
		return subtype;
	}

	public void setSubtype(byte subtype) {
		this.subtype = subtype;
	}

	public long getModifier() {
		return modifier;
	}

	public void setModifier(long modifier) {
		this.modifier = modifier;
	}

	public long getMinusModifier() {
		return minusModifier;
	}

	public void setMinusModifier(long minusModifier) {
		this.minusModifier = minusModifier;
	}

	public byte getRarity() {
		return rarity;
	}

	public void setRarity(byte rarity) {
		this.rarity = rarity;
	}

	public byte getMaterial() {
		return material;
	}

	public void setMaterial(byte material) {
		this.material = material;
	}

	public byte getFlags() {
		return flags;
	}

	public void setFlags(byte flags) {
		this.flags = flags;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GItemUpgrade[] getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(ItemUpgrade[] upgrades) {
		this.upgrades = (GItemUpgrade[]) upgrades;
	}

	public long getUpgradeCount() {
		return upgradeCount;
	}

	public void setUpgradeCount(long upgradeCount) {
		this.upgradeCount = upgradeCount;
	}
}
