package org.glydar.glydar.protocol.data;

import io.netty.buffer.ByteBuf;

import org.glydar.paraglydar.geom.FloatVector3;
import org.glydar.paraglydar.geom.LongVector3;
import org.glydar.paraglydar.geom.Orientation;

public final class DataBufs {

	public static FloatVector3 readFloatVector3(ByteBuf buf) {
		return new FloatVector3(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public static LongVector3 readLongVector3(ByteBuf buf) {
		return new LongVector3(buf.readLong(), buf.readLong(), buf.readLong());
	}

	public static Orientation readOrientation(ByteBuf buf) {
		return new Orientation(buf.readFloat(), buf.readFloat(), buf.readFloat());
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

	public static void writeOrientation(ByteBuf buf, Orientation orientation) {
		buf.writeFloat(orientation.getRoll());
		buf.writeFloat(orientation.getPitch());
		buf.writeFloat(orientation.getYaw());
	}
}
