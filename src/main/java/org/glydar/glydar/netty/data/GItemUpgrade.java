package org.glydar.glydar.netty.data;

import org.glydar.api.data.ItemUpgrade;

import io.netty.buffer.ByteBuf;

public class GItemUpgrade implements BaseData, ItemUpgrade {
	byte xOffset;
	byte yOffset;
	byte zOffset;
	byte material;
	long level;

	public GItemUpgrade() {}
	
	public GItemUpgrade(ItemUpgrade i) {
		this.xOffset = i.getxOffset();
		this.yOffset = i.getyOffset();
		this.zOffset = i.getzOffset();
		this.material = i.getMaterial();
		this.level = i.getLevel();
	}

	@Override
	public void decode(ByteBuf buf) {
		xOffset = buf.readByte();
		yOffset = buf.readByte();
		zOffset = buf.readByte();
		material = buf.readByte();
		level = buf.readUnsignedInt();
	}

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(xOffset);
        buf.writeByte(yOffset);
        buf.writeByte(zOffset);
        buf.writeByte(material);
        buf.writeInt((int)level);
    }

	public byte getxOffset() {
		return xOffset;
	}

	public void setxOffset(byte xOffset) {
		this.xOffset = xOffset;
	}

	public byte getyOffset() {
		return yOffset;
	}

	public void setyOffset(byte yOffset) {
		this.yOffset = yOffset;
	}

	public byte getzOffset() {
		return zOffset;
	}

	public void setzOffset(byte zOffset) {
		this.zOffset = zOffset;
	}

	public byte getMaterial() {
		return material;
	}

	public void setMaterial(byte material) {
		this.material = material;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

}
