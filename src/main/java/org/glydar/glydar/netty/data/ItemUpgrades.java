package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class ItemUpgrades implements BaseData {
	ItemUpgrade[] itemUpgrades = new ItemUpgrade[32];
	long usedUpgrades;

	@Override
	public void decode(ByteBuf buf) {
		for(int i = 0; i < 32; i++) {
            itemUpgrades[i] = new ItemUpgrade();
			itemUpgrades[i].decode(buf);
		}
		usedUpgrades = buf.readUnsignedInt();
	}

    @Override
    public void encode(ByteBuf buf) {
        for(int i = 0; i < 32; i++) {
            itemUpgrades[i].encode(buf);
        }
        buf.writeInt((int)usedUpgrades);
    }
}
