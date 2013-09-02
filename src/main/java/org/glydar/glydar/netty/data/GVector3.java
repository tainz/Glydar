package org.glydar.glydar.netty.data;

import org.glydar.glydar.Glydar;
import org.glydar.paraglydar.data.Vector3;

import io.netty.buffer.ByteBuf;

public class GVector3<T> implements Vector3<T> {
	private T x;
	private T y;
	private T z;

	public GVector3() {
	}

	public GVector3(Vector3<T> v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}

	@SuppressWarnings("unchecked")
	public void decode(ByteBuf buf, Class<T> type) {
		if (Float.class.isAssignableFrom(type)) {
			x = (T) (Float) buf.readFloat();
			y = (T) (Float) buf.readFloat();
			z = (T) (Float) buf.readFloat();
		} else if (Integer.class.isAssignableFrom(type)) {
			x = (T) (Integer) buf.readInt();
			y = (T) (Integer) buf.readInt();
			z = (T) (Integer) buf.readInt();
		} else if (Long.class.isAssignableFrom(type)) {
			x = (T) (Long) buf.readLong();
			y = (T) (Long) buf.readLong();
			z = (T) (Long) buf.readLong();
		} else { //Not sure why this would happen, but just in-case
			x = (T) (Float) buf.readFloat();
			y = (T) (Float) buf.readFloat();
			z = (T) (Float) buf.readFloat();
		}

	}

	public void encode(ByteBuf buf, Class<T> type) {
		if (Float.class.isAssignableFrom(type)) {
			buf.writeFloat((float) (Float) x);
			buf.writeFloat((float) (Float) y);
			buf.writeFloat((float) (Float) z);
		} else if (Integer.class.isAssignableFrom(type)) {
			buf.writeInt((int) (Integer) x);
			buf.writeInt((int) (Integer) y);
			buf.writeInt((int) (Integer) z);
		} else if (Long.class.isAssignableFrom(type)) {
			buf.writeLong((long) (Long) x);
			buf.writeLong((long) (Long) y);
			buf.writeLong((long) (Long) z);
		} else { //Not sure why this would happen, but just in-case
			buf.writeFloat((float) (Float) x);
			buf.writeFloat((float) (Float) y);
			buf.writeFloat((float) (Float) z);
		}

	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}

	public T getZ() {
		return z;
	}

	public void setX(T x) {
		this.x = x;
	}

	public void setY(T y) {
		this.y = y;
	}

	public void setZ(T z) {
		this.z = z;
	}

}
