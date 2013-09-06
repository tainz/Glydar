package org.glydar.glydar.protocol.data;

import static org.glydar.glydar.util.VectorBuf.readFloatVector3;
import static org.glydar.glydar.util.VectorBuf.writeFloatVector3;

import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.geom.FloatVector3;

import io.netty.buffer.ByteBuf;

public class GAppearance implements BaseData, Appearance {

	byte notUsed1, notUsed2;
	byte hairR, hairG, hairB;
	byte movementFlags, entityFlags;
	float scale;
	float boundingRadius;
	float boundingHeight;
	int headModel, hairModel, handModel, footModel, bodyModel, backModel, shoulderModel, wingModel; //ushort
	float headScale, hairScale, handScale, footScale, bodyScale, backScale, unknown, wingScale, shoulderScale, weaponScale;
	float bodyPitch, armPitch, armRoll, armYaw;
	float feetPitch, wingPitch, backPitch;
	FloatVector3 bodyOffset, headOffset, handOffset, footOffset, backOffset, wingOffset;

	public GAppearance() {
		bodyOffset = new FloatVector3();
		headOffset = new FloatVector3();
		handOffset = new FloatVector3();
		footOffset = new FloatVector3();
		backOffset = new FloatVector3();
		wingOffset = new FloatVector3();
	}


	public GAppearance(Appearance a) {
		this.notUsed1 = a.getNotUsed1();
		this.notUsed2 = a.getNotUsed2();
		this.hairR = a.getHairR();
		this.hairG = a.getHairG();
		this.hairB = a.getHairB();
		this.movementFlags = a.getMovementFlags();
		this.entityFlags = a.getEntityFlags();
		this.scale = a.getScale();
		this.boundingRadius = a.getBoundingRadius();
		this.boundingHeight = a.getBoundingHeight();
		this.headModel = a.getHeadModel();
		this.hairModel = a.getHairModel();
		this.handModel = a.getHandModel();
		this.footModel = a.getFootModel();
		this.bodyModel = a.getBodyModel();
		this.backModel = a.getBackModel();
		this.shoulderModel = a.getShoulderModel();
		this.wingModel = a.getWingModel();
		this.headScale = a.getHeadScale();
		this.hairScale = a.getHairScale();
		this.handScale = a.getHandScale();
		this.footScale = a.getFootScale();
		this.bodyScale = a.getBodyScale();
		this.backScale = a.getBackScale();
		this.unknown = a.getUnknown();
		this.wingScale = a.getWingScale();
		this.bodyPitch = a.getBodyPitch();
		this.armPitch = a.getArmPitch();
		this.armRoll = a.getArmRoll();
		this.armYaw = a.getArmYaw();
		this.feetPitch = a.getFeetPitch();
		this.wingPitch = a.getWingPitch();
		this.backPitch = a.getBackPitch();
		this.bodyOffset = a.getBodyOffset();
		this.headOffset = a.getHeadOffset();
		this.handOffset = a.getHandOffset();
		this.footOffset = a.getFootOffset();
		this.backOffset = a.getBackOffset();
		this.wingOffset = a.getWingOffset();
	}


	@Override
	public void decode(ByteBuf buf) {
		notUsed1 = buf.readByte();
		notUsed2 = buf.readByte();
		hairR = buf.readByte();
		hairG = buf.readByte();
		hairB = buf.readByte();
		buf.readByte(); //Skip
		movementFlags = buf.readByte();
		entityFlags = buf.readByte();
		scale = buf.readFloat();
		boundingRadius = buf.readFloat();
		boundingHeight = buf.readFloat();
		headModel = buf.readUnsignedShort();
		hairModel = buf.readUnsignedShort();
		handModel = buf.readUnsignedShort();
		footModel = buf.readUnsignedShort();
		bodyModel = buf.readUnsignedShort();
		backModel = buf.readUnsignedShort();
		shoulderModel = buf.readUnsignedShort();
		wingModel = buf.readUnsignedShort();
		headScale = buf.readFloat();
		bodyScale = buf.readFloat();
		handScale = buf.readFloat();
		footScale = buf.readFloat();
		shoulderScale = buf.readFloat();
		weaponScale = buf.readFloat();
		backScale = buf.readFloat();
		unknown = buf.readFloat();
		wingScale = buf.readFloat();
		bodyPitch = buf.readFloat();
		armPitch = buf.readFloat();
		armRoll = buf.readFloat();
		armYaw = buf.readFloat();
		feetPitch = buf.readFloat();
		wingPitch = buf.readFloat();
		backPitch = buf.readFloat();
		bodyOffset = readFloatVector3(buf);
		headOffset = readFloatVector3(buf);
		handOffset = readFloatVector3(buf);
		footOffset = readFloatVector3(buf);
		backOffset = readFloatVector3(buf);
		wingOffset = readFloatVector3(buf);
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeByte(notUsed1);
		buf.writeByte(notUsed2);
		buf.writeByte(hairR);
		buf.writeByte(hairG);
		buf.writeByte(hairB);
		buf.writeBytes(new byte[1]);
		buf.writeByte(movementFlags);
		buf.writeByte(entityFlags);
		buf.writeFloat(scale);
		buf.writeFloat(boundingRadius);
		buf.writeFloat(boundingHeight);
		buf.writeShort(headModel);
		buf.writeShort(hairModel);
		buf.writeShort(handModel);
		buf.writeShort(footModel);
		buf.writeShort(bodyModel);
		buf.writeShort(backModel);
		buf.writeShort(shoulderModel);
		buf.writeShort(wingModel);
		buf.writeFloat(headScale);
		buf.writeFloat(bodyScale);
		buf.writeFloat(handScale);
		buf.writeFloat(footScale);
		buf.writeFloat(shoulderScale);
		buf.writeFloat(weaponScale);
		buf.writeFloat(backScale);
		buf.writeFloat(unknown);
		buf.writeFloat(wingScale);
		buf.writeFloat(bodyPitch);
		buf.writeFloat(armPitch);
		buf.writeFloat(armRoll);
		buf.writeFloat(armYaw);
		buf.writeFloat(feetPitch);
		buf.writeFloat(wingPitch);
		buf.writeFloat(backPitch);
		writeFloatVector3(buf, bodyOffset);
		writeFloatVector3(buf, headOffset);
		writeFloatVector3(buf, handOffset);
		writeFloatVector3(buf, footOffset);
		writeFloatVector3(buf, backOffset);
		writeFloatVector3(buf, wingOffset);
	}

	public byte getNotUsed1() {
		return notUsed1;
	}

	public void setNotUsed1(byte notUsed1) {
		this.notUsed1 = notUsed1;
	}

	public byte getNotUsed2() {
		return notUsed2;
	}

	public void setNotUsed2(byte notUsed2) {
		this.notUsed2 = notUsed2;
	}

	public byte getHairR() {
		return hairR;
	}

	public void setHairR(byte hairR) {
		this.hairR = hairR;
	}

	public byte getHairG() {
		return hairG;
	}

	public void setHairG(byte hairG) {
		this.hairG = hairG;
	}

	public byte getHairB() {
		return hairB;
	}

	public void setHairB(byte hairB) {
		this.hairB = hairB;
	}

	public byte getMovementFlags() {
		return movementFlags;
	}

	public void setMovementFlags(byte movementFlags) {
		this.movementFlags = movementFlags;
	}

	public byte getEntityFlags() {
		return entityFlags;
	}

	public void setEntityFlags(byte entityFlags) {
		this.entityFlags = entityFlags;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getBoundingRadius() {
		return boundingRadius;
	}

	public void setBoundingRadius(float boundingRadius) {
		this.boundingRadius = boundingRadius;
	}

	public float getBoundingHeight() {
		return boundingHeight;
	}

	public void setBoundingHeight(float boundingHeight) {
		this.boundingHeight = boundingHeight;
	}

	public int getHeadModel() {
		return headModel;
	}

	public void setHeadModel(int headModel) {
		this.headModel = headModel;
	}

	public int getHairModel() {
		return hairModel;
	}

	public void setHairModel(int hairModel) {
		this.hairModel = hairModel;
	}

	public int getHandModel() {
		return handModel;
	}

	public void setHandModel(int handModel) {
		this.handModel = handModel;
	}

	public int getFootModel() {
		return footModel;
	}

	public void setFootModel(int footModel) {
		this.footModel = footModel;
	}

	public int getBodyModel() {
		return bodyModel;
	}

	public void setBodyModel(int bodyModel) {
		this.bodyModel = bodyModel;
	}

	public int getBackModel() {
		return backModel;
	}

	public void setBackModel(int backModel) {
		this.backModel = backModel;
	}

	public int getShoulderModel() {
		return shoulderModel;
	}

	public void setShoulderModel(int shoulderModel) {
		this.shoulderModel = shoulderModel;
	}

	public int getWingModel() {
		return wingModel;
	}

	public void setWingModel(int wingModel) {
		this.wingModel = wingModel;
	}

	public float getHeadScale() {
		return headScale;
	}

	public void setHeadScale(float headScale) {
		this.headScale = headScale;
	}

	public float getHairScale() {
		return hairScale;
	}

	public void setHairScale(float hairScale) {
		this.hairScale = hairScale;
	}

	public float getHandScale() {
		return handScale;
	}

	public void setHandScale(float handScale) {
		this.handScale = handScale;
	}

	public float getFootScale() {
		return footScale;
	}

	public void setFootScale(float footScale) {
		this.footScale = footScale;
	}

	public float getBodyScale() {
		return bodyScale;
	}

	public void setBodyScale(float bodyScale) {
		this.bodyScale = bodyScale;
	}

	public float getBackScale() {
		return backScale;
	}

	public void setBackScale(float backScale) {
		this.backScale = backScale;
	}

	public float getUnknown() {
		return unknown;
	}

	public void setUnknown(float unknown) {
		this.unknown = unknown;
	}

	public float getWingScale() {
		return wingScale;
	}

	public void setWingScale(float wingScale) {
		this.wingScale = wingScale;
	}

	public float getBodyPitch() {
		return bodyPitch;
	}

	public void setBodyPitch(float bodyPitch) {
		this.bodyPitch = bodyPitch;
	}

	public float getArmPitch() {
		return armPitch;
	}

	public void setArmPitch(float armPitch) {
		this.armPitch = armPitch;
	}

	public float getArmRoll() {
		return armRoll;
	}

	public void setArmRoll(float armRoll) {
		this.armRoll = armRoll;
	}

	public float getArmYaw() {
		return armYaw;
	}

	public void setArmYaw(float armYaw) {
		this.armYaw = armYaw;
	}

	public float getFeetPitch() {
		return feetPitch;
	}

	public void setFeetPitch(float feetPitch) {
		this.feetPitch = feetPitch;
	}

	public float getWingPitch() {
		return wingPitch;
	}

	public void setWingPitch(float wingPitch) {
		this.wingPitch = wingPitch;
	}

	public float getBackPitch() {
		return backPitch;
	}

	public void setBackPitch(float backPitch) {
		this.backPitch = backPitch;
	}

	public FloatVector3 getBodyOffset() {
		return bodyOffset;
	}

	public void setBodyOffset(FloatVector3 bodyOffset) {
		this.bodyOffset = bodyOffset;
	}

	public FloatVector3 getHeadOffset() {
		return headOffset;
	}

	public void setHeadOffset(FloatVector3 headOffset) {
		this.headOffset = headOffset;
	}

	public FloatVector3 getHandOffset() {
		return handOffset;
	}

	public void setHandOffset(FloatVector3 handOffset) {
		this.handOffset = handOffset;
	}

	public FloatVector3 getFootOffset() {
		return footOffset;
	}

	public void setFootOffset(FloatVector3 footOffset) {
		this.footOffset = footOffset;
	}

	public FloatVector3 getBackOffset() {
		return backOffset;
	}

	public void setBackOffset(FloatVector3 backOffset) {
		this.backOffset = backOffset;
	}

	public FloatVector3 getWingOffset() {
		return wingOffset;
	}

	public void setWingOffset(FloatVector3 wingOffset) {
		this.wingOffset = wingOffset;
	}
}
