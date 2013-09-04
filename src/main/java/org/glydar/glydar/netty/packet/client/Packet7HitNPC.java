package org.glydar.glydar.netty.packet.client;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.models.GWorld;
import org.glydar.glydar.netty.data.GServerUpdateData;
import org.glydar.glydar.netty.data.GVector3;
import org.glydar.glydar.netty.data.actions.GKillAction;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;

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
	
	public Packet7HitNPC(GPlayer ply){
		id = ply.getEntityId();
		targetId = ply.getEntityId();
		damage = -100F;
		critical = (byte) 0;
		stunDuration = 0;
		unknown = 0;
		position = new GVector3<Long>(ply.getEntityData().getPosition());
		hitDirection = new GVector3<Float>(ply.getEntityData().getExtraVel());
		skillHit = (byte) 0;
		type = (byte) 0;
		showLight = (byte) 0;
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
		Entity hurt = Glydar.getServer().getEntityByEntityID(targetId);
		Entity attacker = Glydar.getServer().getEntityByEntityID(id);
		
		hurt.getEntityData().setHP(hurt.getEntityData().getHP() - damage);
		if (!(hurt instanceof Player)){
			hurt.forceUpdateData();
		}
		
		if (((GWorld) hurt.getWorld()).worldUpdatePacket.sud == null) {
			((GWorld) hurt.getWorld()).worldUpdatePacket.sud = new GServerUpdateData();
		}
		
		((GWorld) hurt.getWorld()).worldUpdatePacket.sud.hitPackets.add(this);
		
		if (hurt.getEntityData().getHP() <= 0){
			GKillAction ka = new GKillAction();
			ka.setId(id);
			ka.setTargetId(targetId);
			if (attacker != null && hurt != null ){
				if (attacker.getEntityData().getLevel() > 0 && hurt.getEntityData().getLevel() > 0)
				//TODO: Figure out a better equation!
				ka.setXp((int) (hurt.getEntityData().getLevel()/attacker.getEntityData().getLevel() * 5));
			} else {
				ka.setXp(5);
			}
			
			((GWorld) hurt.getWorld()).worldUpdatePacket.sud.killActions.add(ka);
		}
		
	}
}
