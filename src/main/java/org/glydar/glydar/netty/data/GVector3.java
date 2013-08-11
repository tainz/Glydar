package org.glydar.glydar.netty.data;

import org.glydar.glydar.api.data.Vector3;

import io.netty.buffer.ByteBuf;

public class GVector3 implements Vector3, BaseData {
    public float x;
    public float y;
    public float z;

    @Override
    public void decode(ByteBuf buf) {
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
    }

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void setZ(float z) {
		this.z = z;
	}
}
