package org.glydar.glydar.util;

import io.netty.buffer.ByteBuf;

import org.glydar.paraglydar.geom.FloatVector3;
import org.glydar.paraglydar.geom.LongVector3;

public final class VectorBuf {

	public static FloatVector3 readFloatVector3(ByteBuf buf) {
		return new FloatVector3(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public static LongVector3 readLongVector3(ByteBuf buf) {
		return new LongVector3(buf.readLong(), buf.readLong(), buf.readLong());
	}

	public static void writeFloatVector3(ByteBuf buf, FloatVector3 vector) {
		buf.writeFloat(vector.getX());
		buf.writeFloat(vector.getY());
		buf.writeFloat(vector.getZ());
	}

	public static void writeLongVector3(ByteBuf buf, LongVector3 vector) {
		buf.writeLong(vector.getX());
		buf.writeLong(vector.getY());
		buf.writeLong(vector.getZ());
	}
}
