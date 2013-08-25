package org.glydar.api.data;

public interface Item {

	public byte getType();

	public void setType(byte type);

	public byte getSubtype();

	public void setSubtype(byte subtype);

	public long getModifier();

	public void setModifier(long modifier);

	public long getMinusModifier();

	public void setMinusModifier(long minusModifier);

	public byte getRarity();

	public void setRarity(byte rarity);

	public byte getMaterial();

	public void setMaterial(byte material);

	public byte getFlags();

	public void setFlags(byte flags);

	public int getLevel();

	public void setLevel(int level);

	public ItemUpgrade[] getUpgrades();

	public void setUpgrades(ItemUpgrade[] upgrades);

	public long getUpgradeCount();

	public void setUpgradeCount(long upgradeCount);
	
}
