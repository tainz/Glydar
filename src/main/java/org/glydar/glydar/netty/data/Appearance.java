package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class Appearance implements BaseData {

    byte notUsed1, notUsed2;
    byte hairR, hairG, hairB;
    byte movementFlags, entityFlags;
    float scale;
    float boundingRadius;
    float boundingHeight;
    int headModel, hairModel, handModel, footModel, bodyModel, backModel, shoulderModel, wingModel; //ushort
    float headScale, hairScale, handScale, footScale, bodyScale, backScale, unknown, wingScale;
    float bodyPitch, armPitch, armRoll, armYaw;
    float feetPitch, wingPitch, backPitch;
    Vector3 bodyOffset, headOffset, handOffset, footOffset, backOffset, wingOffset;

    public Appearance() {
        bodyOffset = new Vector3();
        headOffset = new Vector3();
        handOffset = new Vector3();
        footOffset = new Vector3();
        backOffset = new Vector3();
        wingOffset = new Vector3();
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
        hairScale = buf.readFloat();
        handScale = buf.readFloat();
        footScale = buf.readFloat();
        bodyScale = buf.readFloat();
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
        bodyOffset.decode(buf);
        headOffset.decode(buf);
        handOffset.decode(buf);
        footOffset.decode(buf);
        backOffset.decode(buf);
        wingOffset.decode(buf);

    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte(notUsed1);
        buf.writeByte(notUsed2);
        buf.writeByte(hairR);
        buf.writeByte(hairG);
        buf.writeByte(hairB);
        buf.writeByte((byte)0);
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
        buf.writeFloat(hairScale);
        buf.writeFloat(handScale);
        buf.writeFloat(footScale);
        buf.writeFloat(bodyScale);
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
        bodyOffset.encode(buf);
        headOffset.encode(buf);
        handOffset.encode(buf);
        footOffset.encode(buf);
        backOffset.encode(buf);
        wingOffset.encode(buf);
    }
}
