package org.glydar.glydar.netty.data;

import org.glydar.api.data.Vector3;

import io.netty.buffer.ByteBuf;

public class GVector3<T> implements Vector3<T>, BaseData {
	public Object type;
    public T x;
    public T y;
    public T z;

    public GVector3 (Object type){
    	this.type = type;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void decode(ByteBuf buf) {
    	if (type instanceof Float){
    		x = (T) (Float) buf.readFloat();
            y = (T) (Float) buf.readFloat();
            z = (T) (Float) buf.readFloat();
    	} else if (type instanceof Integer){
    		x = (T) (Integer) buf.readInt();
            y = (T) (Integer) buf.readInt();
            z = (T) (Integer) buf.readInt();
    	} else if (type instanceof Long){
    		x = (T) (Long) buf.readLong();
            y = (T) (Long) buf.readLong();
            z = (T) (Long) buf.readLong();
    	} else { //Not sure why this would happen, but just in-case
    		x = (T) (Float) buf.readFloat();
            y = (T) (Float) buf.readFloat();
            z = (T) (Float) buf.readFloat();
    	}
        
    }

    @Override
    public void encode(ByteBuf buf) {
    	if (type instanceof Float){
	        buf.writeFloat((float) (Float) x);
	        buf.writeFloat((float) (Float) y);
	        buf.writeFloat((float) (Float) z);
    	} else if (type instanceof Integer){
	        buf.writeInt((int) (Integer) x);
	        buf.writeInt((int) (Integer) y);
	        buf.writeInt((int) (Integer) z);
    	} else if (type instanceof Long){
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
