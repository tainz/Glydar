package org.glydar.glydar.protocol.client;

import static org.glydar.glydar.util.VectorBuf.readFloatVector3;
import static org.glydar.glydar.util.VectorBuf.readLongVector3;
import static org.glydar.glydar.util.VectorBuf.writeFloatVector3;
import static org.glydar.glydar.util.VectorBuf.writeLongVector3;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.models.GWorld;
import org.glydar.glydar.protocol.data.GServerUpdateData;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;
import org.glydar.paraglydar.geom.FloatVector3;
import org.glydar.paraglydar.geom.LongVector3;
import org.glydar.paraglydar.models.Entity;

@PacketType(id = 9)
public class Packet9ShootProjectile extends Packet {
	long entID; //Unsigned!

	int chunkX;
	int chunkY;

	long something5; //Unsigned Int!

	LongVector3 position;

	long something13; //uint
	long something14; //uint
	long something15; //uint

	FloatVector3 velocity;

	float something19; // rand() ??
	float something20;
	float something21;
	float something22; // ?????
	long something23; //uint
	byte something24;
	long something25; //uint
	byte something26;
	long something27; //uint
	long something28; //uint

	public Packet9ShootProjectile() {
		position = new LongVector3();
		velocity = new FloatVector3();
	}

	@Override
	protected void internalDecode(ByteBuf buf) {
		entID = buf.readLong(); //Unsigned long actually!

		chunkX = buf.readInt();
		chunkY = buf.readInt();

		something5 = buf.readUnsignedInt();
		buf.readBytes(4); //Padding

		position = readLongVector3(buf);

		something13 = buf.readUnsignedInt();
		something14 = buf.readUnsignedInt();
		something15 = buf.readUnsignedInt();

		velocity = readFloatVector3(buf);

		something19 = buf.readFloat();
		something20 = buf.readFloat();
		something21 = buf.readFloat();
		something22 = buf.readFloat();
		something23 = buf.readUnsignedInt();
		something24 = buf.readByte();
		buf.readBytes(3); //Padding
		something25 = buf.readUnsignedInt();
		something26 = buf.readByte();
		buf.readBytes(3); //Padding
		something27 = buf.readUnsignedInt();
		something28 = buf.readUnsignedInt();
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(entID);
		buf.writeInt(chunkX);
		buf.writeInt(chunkY);
		buf.writeInt((int) something5);
		buf.writeBytes(new byte[4]);
		writeLongVector3(buf, position);
		buf.writeInt((int) something13);
		buf.writeInt((int) something14);
		buf.writeInt((int) something15);
		writeFloatVector3(buf, velocity);
		buf.writeFloat(something19);
		buf.writeFloat(something20);
		buf.writeFloat(something21);
		buf.writeFloat(something22);
		buf.writeInt((int) something23);
		buf.writeByte(something24);
		buf.writeBytes(new byte[3]);
		buf.writeInt((int) something25);
		buf.writeByte(something26);
		buf.writeBytes(new byte[3]);
		buf.writeInt((int) something27);
		buf.writeInt((int) something28);
	}

	//TODO: Add to server update packet!

	@Override
	public void receivedFrom(GPlayer ply) {
		Entity shooter = Glydar.getServer().getEntityByEntityID(entID);
		if (((GWorld) shooter.getWorld()).worldUpdatePacket.sud == null) {
			((GWorld) shooter.getWorld()).worldUpdatePacket.sud = new GServerUpdateData();
		}
		((GWorld) shooter.getWorld()).worldUpdatePacket.sud.shootPackets.add(this);
	}
}
