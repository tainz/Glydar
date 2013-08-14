package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;

import org.glydar.api.data.Vector3;
import org.glydar.glydar.netty.data.GVector3;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 7)
public class Packet7HitNPC extends CubeWorldPacket {
	long id;
	long targetId;
	float damage;
	byte critical;
	long stunDuration; //uint
	long unknown; //uint
	GVector3<Long> position;
	GVector3<Float> hitDirection;
	byte skillHit;
	byte type;
	byte showLight;

	@Override
	protected void internalDecode(ByteBuf buf) {
		id = buf.readLong();
		targetId = buf.readLong();
		damage = buf.readFloat();
		critical = buf.readByte();
		buf.readBytes(3);
		stunDuration = buf.readUnsignedInt();
		unknown = buf.readUnsignedInt();
		position.decode(buf, Long.class);
		hitDirection.decode(buf, Float.class);
		skillHit = buf.readByte();
		type = buf.readByte();
		showLight = buf.readByte();
		buf.readByte();
	}
	
	@Override
	protected void internalEncode(ByteBuf buf) {
		buf.writeLong(id);
		buf.writeLong(targetId);
		buf.writeFloat(damage);
		buf.writeByte(critical);
		buf.writeBytes(new byte[3]);
		buf.writeInt((int) stunDuration);
		buf.writeInt((int) unknown);
		position.encode(buf, Long.class);
		hitDirection.encode(buf, Float.class);
		buf.writeByte(skillHit);
		buf.writeByte(type);
		buf.writeByte(showLight);
		buf.writeByte((byte)0);
	}
}
