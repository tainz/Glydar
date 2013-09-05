package org.glydar.glydar.netty.packet.client;

import static org.glydar.glydar.util.VectorBuf.readFloatVector3;
import static org.glydar.glydar.util.VectorBuf.readLongVector3;
import static org.glydar.glydar.util.VectorBuf.writeFloatVector3;
import static org.glydar.glydar.util.VectorBuf.writeLongVector3;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.models.GWorld;
import org.glydar.glydar.netty.data.GServerUpdateData;
import org.glydar.glydar.netty.data.actions.GKillAction;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.paraglydar.geom.FloatVector3;
import org.glydar.paraglydar.geom.LongVector3;
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
	LongVector3 position;
	FloatVector3 hitDirection;
	byte skillHit;
	byte type;
	byte showLight;

	public Packet7HitNPC() {
		position = new LongVector3();
		hitDirection = new FloatVector3();
	}
	
	public Packet7HitNPC(GPlayer ply){
		id = ply.getEntityId();
		targetId = ply.getEntityId();
		damage = -100F;
		critical = (byte) 0;
		stunDuration = 0;
		unknown = 0;
		position = ply.getEntityData().getPosition();
		hitDirection = ply.getEntityData().getExtraVel();
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
		position = readLongVector3(buf);
		hitDirection = readFloatVector3(buf);
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
		writeLongVector3(buf, position);
		writeFloatVector3(buf, hitDirection);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public byte getCritical() {
		return critical;
	}

	public void setCritical(byte critical) {
		this.critical = critical;
	}

	public long getStunDuration() {
		return stunDuration;
	}

	public void setStunDuration(long stunDuration) {
		this.stunDuration = stunDuration;
	}

	public long getUnknown() {
		return unknown;
	}

	public void setUnknown(long unknown) {
		this.unknown = unknown;
	}

	public LongVector3 getPosition() {
		return position;
	}

	public void setPosition(LongVector3 position) {
		this.position = position;
	}

	public FloatVector3 getHitDirection() {
		return hitDirection;
	}

	public void setHitDirection(FloatVector3 hitDirection) {
		this.hitDirection = hitDirection;
	}

	public byte getSkillHit() {
		return skillHit;
	}

	public void setSkillHit(byte skillHit) {
		this.skillHit = skillHit;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getShowLight() {
		return showLight;
	}

	public void setShowLight(byte showLight) {
		this.showLight = showLight;
	}
}
