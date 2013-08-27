package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.data.GServerUpdateData;
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

	public Packet7HitNPC() {
		position = new GVector3<Long>();
		hitDirection = new GVector3<Float>();
	}

	@Override
	public void internalDecode(ByteBuf buf) {
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
	public void internalEncode(ByteBuf buf) {
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
		buf.writeByte((byte) 0);
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		if (Glydar.getServer().serverUpdatePacket.sud == null) {
			Glydar.getServer().serverUpdatePacket.sud = new GServerUpdateData();
		}
		Glydar.getServer().serverUpdatePacket.sud.hitPackets.add(this);
	}
}
