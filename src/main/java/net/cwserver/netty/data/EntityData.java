package net.cwserver.netty.data;

import io.netty.buffer.ByteBuf;

public class EntityData implements BaseData {

    long id;
    byte[] bitmask;

    long posX;
    long posY;
    long posZ;

    float rotX;
    float rotY;
    float rotZ;

    float velX;
    float velY;
    float velZ;

    float accX;
    float accY;
    float accZ;

    float extraVelX;
    float extraVelY;
    float extraVelZ;

    float lookPitch;
    long physicsFlags; //Uint
    byte speedFlags;
    long entityType; //Uint
    byte currentMode;
    long lastShootTime; //Uint
    long hitCounter; //Uint
    long lastHitTime; //Uint
    Appearance app;
    byte flags1;
    byte flags2;
    long rollTime; //Uint
    int stunTime;
    long slowedTime; //Uint
    long makeBlueTime; //Uint
    long speedUpTime; //Uint
    float slowPatchTime;
    byte classType;
    byte specialization;
    float chargedMP;

    float rayHitX;
    float rayHitY;
    float rayHitZ;

    float HP;
    float MP;

    float blockPower;
    float maxHPMultiplier;
    float shootSpeed;
    float damageMultiplier;
    float armorMultiplier;
    float resistanceMultiplier;
    long level;  //Uint
    long currentXP; //Uint
    Item itemData;
    Item[] equipment;

    long iceBlockFour; //Uint
    long[] skills;
    String name;

    long na1; //Uint
    long na2; // |
    byte na3;
    long na4;
    long na5;
    long nu1;
    long nu2;
    long nu3;
    long nu4;
    long nu5;
    long nu6;
    byte nu7;
    byte nu8;
    long parentOwner;
    long nu9;
    long nu10;
    long nu11;
    long nu12;
    long nu13;
    long nu14;
    long nu15;
    long nu16;
    long nu17;
    long nu18;
    long nu20;
    long nu21;
    long nu22;
    byte nu19;

    public EntityData() {
        app = new Appearance();
    }

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void encode(ByteBuf buf) {

    }
}
