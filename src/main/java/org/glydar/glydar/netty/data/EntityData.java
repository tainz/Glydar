package org.glydar.glydar.netty.data;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.util.Bitops;

import sun.security.util.BitArray;

import java.nio.ByteOrder;

/* Structures and data discovered by mat^2 (http://github.com/matpow2) */

public class EntityData implements BaseData {

	private long id;
	private byte[] bitmask = new byte[8];

	private long posX;
	private long posY;
	private long posZ;

	private float roll;
	private float pitch;
	private float yaw;

	private Vector3 velocity;

	private Vector3 accel;

	private Vector3 extraVel;

	private float lookPitch;
	private long physicsFlags; //Uint
	private byte speedFlags;
	private long entityType; //Uint
	private byte currentMode;
	private long lastShootTime; //Uint
	private long hitCounter; //Uint
	private long lastHitTime; //Uint
	private Appearance app;
	private byte flags1;
	private byte flags2;
	private long rollTime; //Uint
	private int stunTime;
	private long slowedTime; //Uint
	private long makeBlueTime; //Uint
	private long speedUpTime; //Uint
	private float slowPatchTime;
	private byte classType;
	private byte specialization;
	private float chargedMP;

	private Vector3 rayHit;

	private float HP;
	private float MP;

	private float blockPower;
	private float maxHPMultiplier;
	private float shootSpeed;
	private float damageMultiplier;
	private float armorMultiplier;
	private float resistanceMultiplier;
	private long level;  //Uint
	private long currentXP; //Uint
	private Item itemData;
	private Item[] equipment;

	private long iceBlockFour; //Uint
	private long[] skills;
	private String name;

	private long na1; //Uint
	private long na2; // |
	private byte na3;
	private long na4;
	private long na5;
	private long nu1;
	private long nu2;
	private long nu3;
	private long nu4;
	private long nu5;
	private long nu6;
	private byte nu7;
	private byte nu8;
	private long parentOwner;
	private long nu11;
	private long nu12;
	private long nu13;
	private long nu14;
	private long nu15;
	private long nu16;
	private long nu17;
	private long nu18;
	private long nu20;
	private long nu21;
	private long nu22;
	private byte nu19;

	private int debugCap;

    public EntityData() {
        bitmask = new byte[8];
        velocity = new Vector3();
        accel = new Vector3();
        extraVel = new Vector3();
        rayHit = new Vector3();
        app = new Appearance();
        itemData = new Item();
        equipment = new Item[13];
        for(int i = 0; i < 13; i++)
            equipment[i] = new Item();

        skills = new long[11];

    }

    @Override
    @SuppressWarnings("restriction")
    public void decode(ByteBuf buf) {
        id = buf.readLong();
        buf.readBytes(bitmask);
        BitArray bitArray = new BitArray(8*bitmask.length, Bitops.flipBits(bitmask)); //Size in bits, byte[]
        //BitArray bitArray = new BitArray(bitmask);

        if(bitArray.get(0)) {
            posX = buf.readLong();
            posY = buf.readLong();
            posZ = buf.readLong();
        }
        if(bitArray.get(1)) {
	        pitch = buf.readFloat();
            roll = buf.readFloat();
            yaw = buf.readFloat();
        }
        if(bitArray.get(2)) {
            velocity.decode(buf);
        }
        if(bitArray.get(3)) {
            accel.decode(buf);
        }
        if(bitArray.get(4)) {
            extraVel.decode(buf);
        }
        if(bitArray.get(5)) {
            lookPitch = buf.readFloat();
        }
        if(bitArray.get(6)) {
            physicsFlags = buf.readUnsignedInt();
        }
        if(bitArray.get(7)) {
            speedFlags = buf.readByte();
        }
        if(bitArray.get(8)) {
            entityType = buf.readUnsignedInt();
        }
        if(bitArray.get(9)) {
            currentMode = buf.readByte();
        }
        if (bitArray.get(10)) {
            lastShootTime = buf.readUnsignedInt();
        }
        if(bitArray.get(11)) {
            hitCounter = buf.readUnsignedInt();
        }
        if(bitArray.get(12)) {
            lastHitTime = buf.readUnsignedInt();
        }
        if(bitArray.get(13)) {
            app.decode(buf);
        }
        if(bitArray.get(14)) {
            flags1 = buf.readByte();
            flags2 = buf.readByte();
        }
        if(bitArray.get(15))  {
            rollTime = buf.readUnsignedInt();
        }
        if(bitArray.get(16)) {
            stunTime = buf.readInt();
        }
        if(bitArray.get(17)) {
            slowedTime = buf.readUnsignedInt();
        }
        if(bitArray.get(18)) {
            makeBlueTime = buf.readUnsignedInt();
        }
        if(bitArray.get(19)) {
            speedUpTime = buf.readUnsignedInt();
        }
        if(bitArray.get(20)) {
            slowPatchTime = buf.readFloat();
        }
        if(bitArray.get(21)) {
            classType = buf.readByte();
        }
        if(bitArray.get(22)) {
            specialization = buf.readByte();
        }
        if(bitArray.get(23)) {
            chargedMP = buf.readFloat();
        }
        if(bitArray.get(24)) {
            nu1 = buf.readUnsignedInt();
            nu2 = buf.readUnsignedInt();
            nu3 = buf.readUnsignedInt();
        }
        if(bitArray.get(25)) {
            nu4 = buf.readUnsignedInt();
            nu5 = buf.readUnsignedInt();
            nu6 = buf.readUnsignedInt();
        }
        if(bitArray.get(26)) {
            rayHit.decode(buf);
        }
        if(bitArray.get(27)) {
            HP = buf.readFloat();
        }
        if(bitArray.get(28)) {
            MP = buf.readFloat();
        }
        if(bitArray.get(29)) {
            blockPower = buf.readFloat();
        }
        if(bitArray.get(30)) {
            maxHPMultiplier = buf.readFloat();
            shootSpeed = buf.readFloat();
            damageMultiplier = buf.readFloat();
            armorMultiplier = buf.readFloat();
            resistanceMultiplier = buf.readFloat();
        }
        if(bitArray.get(31)) {
            nu7 = buf.readByte();
        }
        if(bitArray.get(32)) {
            nu8 = buf.readByte();
        }
        if(bitArray.get(33)) {
            level = buf.readUnsignedInt();
        }
        if (bitArray.get(34)) {
            currentXP = buf.readUnsignedInt();
        }
        if(bitArray.get(35)) {
            parentOwner = buf.readLong();
        }
        if(bitArray.get(36)) {
            na1 = buf.readUnsignedInt();
            na2 = buf.readUnsignedInt();
        }
        if(bitArray.get(37)) {
            na3 = buf.readByte();
        }
        if(bitArray.get(38)) {
            na4 = buf.readUnsignedInt();
        }
        if(bitArray.get(39)) {
            na5 = buf.readUnsignedInt();
            nu11 = buf.readUnsignedInt();
            nu12 = buf.readUnsignedInt();
        }
        if(bitArray.get(40)) {
            nu13 = buf.readUnsignedInt();
            nu14 = buf.readUnsignedInt();
            nu15 = buf.readUnsignedInt();
            nu16 = buf.readUnsignedInt();
            nu17 = buf.readUnsignedInt();
            nu18 = buf.readUnsignedInt();
        }
        if(bitArray.get(41)) {
            nu20 = buf.readUnsignedInt();
            nu21 = buf.readUnsignedInt();
            nu22 = buf.readUnsignedInt();
        }
        if(bitArray.get(42)) {
            nu19 = buf.readByte();
        }
        if(bitArray.get(43)) {
            itemData.decode(buf);
        }
        if(bitArray.get(44)) {
            for (int i = 0; i < 13; i++)
            {
                Item item = new Item();
                item.decode(buf);
                equipment[i] = item;
            }
        }
        if(bitArray.get(45)) {
            name = new String(buf.readBytes(16).array(), Charsets.US_ASCII).trim();
        }
        if(bitArray.get(46)) {
            for (int i = 0; i < 11; i++) {
                skills[i] = buf.readUnsignedInt();
            }
        }
        if(bitArray.get(47)) {
            iceBlockFour = buf.readUnsignedInt();
        }

        debugCap = buf.capacity();
        buf.resetReaderIndex();
        buf.resetWriterIndex();

    }

    
    @SuppressWarnings("restriction")
	@Override
    public void encode(ByteBuf buf) {
        buf.writeLong(id); //Ulong but whatever
    	//TODO: Not sure exactly how to approach writing unsigned ints!
    	BitArray bitArray = new BitArray(8*bitmask.length, bitmask); //Size in bits, byte[]
        buf.writeBytes(bitmask);

        if(bitArray.get(0)) {
            buf.writeLong(posX);
            buf.writeLong(posY);
            buf.writeLong(posZ);
        }
        if(bitArray.get(1)) {
	        buf.writeFloat(pitch);
        	buf.writeFloat(roll);
        	buf.writeFloat(yaw);
        }
        if(bitArray.get(2)) {
            velocity.encode(buf);
        }
        if(bitArray.get(3)) {
            accel.encode(buf);
        }
        if(bitArray.get(4)) {
            extraVel.encode(buf);
        }
        if(bitArray.get(5)) {
            buf.writeFloat(lookPitch);
        }
        if(bitArray.get(6)) {
        	buf.writeInt((int)physicsFlags);
        }
        if(bitArray.get(7)) {
        	buf.writeByte(speedFlags);
        }
        if(bitArray.get(8)) {
        	buf.writeInt((int) entityType);
        }
        if(bitArray.get(9)) {
        	buf.writeByte(currentMode);
        }
        if (bitArray.get(10)) {
        	buf.writeInt((int) lastShootTime);
        }
        if(bitArray.get(11)) {
        	buf.writeInt((int) hitCounter);
        }
        if(bitArray.get(12)) {
        	buf.writeInt((int) lastHitTime);
        }
        if(bitArray.get(13)) {
            app.encode(buf);
        }
        if(bitArray.get(14)) {
        	buf.writeByte(flags1);
        	buf.writeByte(flags2);
        }
        if(bitArray.get(15))  {
        	buf.writeInt((int) rollTime);
        }
        if(bitArray.get(16)) {
        	buf.writeInt(stunTime);
        }
        if(bitArray.get(17)) {
        	buf.writeInt((int) slowedTime);
        }
        if(bitArray.get(18)) {
        	buf.writeInt((int) makeBlueTime);
        }
        if(bitArray.get(19)) {
        	buf.writeInt((int) speedUpTime);
        }
        if(bitArray.get(20)) {
        	buf.writeFloat(slowPatchTime);
        }
        if(bitArray.get(21)) {
        	buf.writeByte(classType);
        }
        if(bitArray.get(22)) {
            buf.writeByte(specialization);
        }
        if(bitArray.get(23)) {
        	buf.writeFloat(chargedMP);
        }
        if(bitArray.get(24)) {
        	buf.writeInt((int) nu1);
        	buf.writeInt((int) nu2);
        	buf.writeInt((int) nu3);
        }
        if(bitArray.get(25)) {
        	buf.writeInt((int) nu4);
        	buf.writeInt((int) nu5);
        	buf.writeInt((int) nu6);
        }
        if(bitArray.get(26)) {
            rayHit.encode(buf);
        }
        if(bitArray.get(27)) {
        	buf.writeFloat(HP);
        }
        if(bitArray.get(28)) {
        	buf.writeFloat(MP);
        }
        if(bitArray.get(29)) {
        	buf.writeFloat(blockPower);
        }
        if(bitArray.get(30)) {
        	buf.writeFloat(maxHPMultiplier);
        	buf.writeFloat(shootSpeed);
        	buf.writeFloat(damageMultiplier);
        	buf.writeFloat(armorMultiplier);
        	buf.writeFloat(resistanceMultiplier);
        }
        if(bitArray.get(31)) {
        	buf.writeByte(nu7);
        }
        if(bitArray.get(32)) {
        	buf.writeByte(nu8);
        }
        if(bitArray.get(33)) {
        	buf.writeInt((int) level);
        }
        if (bitArray.get(34)) {
        	buf.writeInt((int) currentXP);
        }
        if(bitArray.get(35)) {
        	buf.writeLong(parentOwner);
        }
        if(bitArray.get(36)) {
        	buf.writeInt((int) na1);
        	buf.writeInt((int) na2);
        }
        if(bitArray.get(37)) {
        	buf.writeByte(na3);
        }
        if(bitArray.get(38)) {
        	buf.writeInt((int) na4);
        }
        if(bitArray.get(39)) {
        	buf.writeInt((int) na5);
        	buf.writeInt((int) nu11);
        	buf.writeInt((int) nu12);
        }
        if(bitArray.get(40)) {
        	buf.writeInt((int) nu13);
        	buf.writeInt((int) nu14);
        	buf.writeInt((int) nu15);
        	buf.writeInt((int) nu16);
        	buf.writeInt((int) nu17);
        	buf.writeInt((int) nu18);
        }
        if(bitArray.get(41)) {
        	buf.writeInt((int) nu20);
        	buf.writeInt((int) nu21);
        	buf.writeInt((int) nu22);
        }
        if(bitArray.get(42)) {
        	buf.writeByte(nu19);
        }
        if(bitArray.get(43)) {
            itemData.encode(buf);
        }
        if(bitArray.get(44)) {
            for (int i = 0; i < 13; i++)
            {
                Item item = equipment[i];
                item.encode(buf);
            }
        }
        if(bitArray.get(45)) {
            byte[] utf8 = name.getBytes(Charsets.UTF_8);
            buf.writeBytes(new byte[4]); //TODO But why?
            buf.writeBytes(utf8);
            buf.writeBytes(new byte[16-name.length()]);

        }
        if(bitArray.get(46)) {
            for (int i = 0; i < 11; i++) {
            	buf.writeInt((int) skills[i]);
            }
        }
        if(bitArray.get(47)) {
        	buf.writeInt((int) iceBlockFour);
        }

        buf.capacity(buf.writerIndex()+1);
        if(buf.readerIndex() > 0) {
            System.out.println("I read something during an encode?!");
        }
    }

    /**
     * Updates this EntityData with another EntityData via bitmask
     * @param changes Bitmasked EntityData with changes.
     */
    @SuppressWarnings("restriction")
    public void updateFrom(EntityData changes) {
        if(changes.id != this.id)
        {
            Glydar.getServer().getLogger().warning("Tried to update entity ID "+this.id+" with changes from ID "+changes.id+"!");
            return;
        }
        
		BitArray bitArray = new BitArray(8*changes.bitmask.length, Bitops.flipBits(changes.bitmask)); //Size in bits, byte[]

        if(bitArray.get(0)) {
            this.posX = changes.posX;
            this.posY = changes.posY;
            this.posZ = changes.posZ;
        }
        if(bitArray.get(1)) {
            this.pitch = changes.pitch;
            this.roll = changes.roll;
            this.yaw = changes.yaw;
        }
        if(bitArray.get(2)) {
            this.velocity = changes.velocity;
        }
        if(bitArray.get(3)) {
            this.accel = changes.accel;
        }
        if(bitArray.get(4)) {
            this.extraVel = changes.extraVel;
        }
        if(bitArray.get(5)) {
            this.lookPitch = changes.lookPitch;
        }
        if(bitArray.get(6)) {
            this.physicsFlags = changes.physicsFlags;
        }
        if(bitArray.get(7)) {
            this.speedFlags = changes.speedFlags;
        }
        if(bitArray.get(8)) {
            this.entityType = changes.entityType;
        }
        if(bitArray.get(9)) {
            this.currentMode = changes.currentMode;
        }
        if (bitArray.get(10)) {
            this.lastShootTime = changes.lastShootTime;
        }
        if(bitArray.get(11)) {
            this.hitCounter = changes.hitCounter;
        }
        if(bitArray.get(12)) {
            this.lastHitTime = changes.lastHitTime;
        }
        if(bitArray.get(13)) {
            this.app = changes.app;
        }
        if(bitArray.get(14)) {
            this.flags1 = changes.flags1;
            this.flags2 = changes.flags2;
        }
        if(bitArray.get(15))  {
            this.rollTime = changes.rollTime;
        }
        if(bitArray.get(16)) {
            this.stunTime = changes.stunTime;
        }
        if(bitArray.get(17)) {
            this.slowedTime = changes.slowedTime;
        }
        if(bitArray.get(18)) {
            this.makeBlueTime = changes.makeBlueTime;
        }
        if(bitArray.get(19)) {
            this.speedUpTime = changes.speedUpTime;
        }
        if(bitArray.get(20)) {
            this.slowPatchTime = changes.slowPatchTime;
        }
        if(bitArray.get(21)) {
            this.classType = changes.classType;
        }
        if(bitArray.get(22)) {
            this.specialization = changes.specialization;
        }
        if(bitArray.get(23)) {
            this.chargedMP = changes.chargedMP;
        }
        if(bitArray.get(24)) {
            this.nu1 = changes.nu1;
            this.nu2 = changes.nu2;
            this.nu3 = changes.nu3;
        }
        if(bitArray.get(25)) {
            this.nu4 = changes.nu4;
            this.nu5 = changes.nu5;
            this.nu6 = changes.nu6;
        }
        if(bitArray.get(26)) {
            this.rayHit = changes.rayHit;
        }
        if(bitArray.get(27)) {
            this.HP = changes.HP;
        }
        if(bitArray.get(28)) {
            this.MP = changes.MP;
        }
        if(bitArray.get(29)) {
            this.blockPower = changes.blockPower;
        }
        if(bitArray.get(30)) {
            this.maxHPMultiplier = changes.maxHPMultiplier;
            this.shootSpeed = changes.shootSpeed;
            this.damageMultiplier = changes.damageMultiplier;
            this.armorMultiplier = changes.armorMultiplier;
            this.resistanceMultiplier = changes.resistanceMultiplier;
        }
        if(bitArray.get(31)) {
            this.nu7 = changes.nu7;
        }
        if(bitArray.get(32)) {
            this.nu8 = changes.nu8;
        }
        if(bitArray.get(33)) {
            this.level = changes.level;
        }
        if (bitArray.get(34)) {
            this.currentXP = changes.currentXP;
        }
        if(bitArray.get(35)) {
            this.parentOwner = changes.parentOwner;
        }
        if(bitArray.get(36)) {
            this.na1 = changes.na1;
            this.na2 = changes.na2;
        }
        if(bitArray.get(37)) {
            this.na3 = changes.na3;
        }
        if(bitArray.get(38)) {
            this.na4 = changes.na4;
        }
        if(bitArray.get(39)) {
            this.na5 = changes.na5;
            this.nu11 = changes.nu11;
            this.nu12 = changes.nu12;
        }
        if(bitArray.get(40)) {
            this.nu13 = changes.nu13;
            this.nu14 = changes.nu14;
            this.nu15 = changes.nu15;
            this.nu16 = changes.nu16;
            this.nu17 = changes.nu17;
            this.nu18 = changes.nu18;
        }
        if(bitArray.get(41)) {
            this.nu20 = changes.nu20;
            this.nu21 = changes.nu21;
            this.nu22 = changes.nu22;
        }
        if(bitArray.get(42)) {
            this.nu19 = changes.nu19;
        }
        if(bitArray.get(43)) {
            this.itemData = changes.itemData;
        }
        if(bitArray.get(44)) {
            this.equipment = changes.equipment;
        }
        if(bitArray.get(45)) {
            this.name = changes.name;
        }
        if(bitArray.get(46)) {
            this.skills = changes.skills;
        }
        if(bitArray.get(47)) {
            this.iceBlockFour = changes.iceBlockFour;
        }
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPosX() {
		return posX;
	}

	public void setPosX(long posX) {
		this.posX = posX;
	}

	public long getPosY() {
		return posY;
	}

	public void setPosY(long posY) {
		this.posY = posY;
	}

	public long getPosZ() {
		return posZ;
	}

	public void setPosZ(long posZ) {
		this.posZ = posZ;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
	}

	public Vector3 getAccel() {
		return accel;
	}

	public void setAccel(Vector3 accel) {
		this.accel = accel;
	}

	public Vector3 getExtraVel() {
		return extraVel;
	}

	public void setExtraVel(Vector3 extraVel) {
		this.extraVel = extraVel;
	}

	public float getLookPitch() {
		return lookPitch;
	}

	public void setLookPitch(float lookPitch) {
		this.lookPitch = lookPitch;
	}

	public long getPhysicsFlags() {
		return physicsFlags;
	}

	public void setPhysicsFlags(long physicsFlags) {
		this.physicsFlags = physicsFlags;
	}

	public byte getSpeedFlags() {
		return speedFlags;
	}

	public void setSpeedFlags(byte speedFlags) {
		this.speedFlags = speedFlags;
	}

	public long getEntityType() {
		return entityType;
	}

	public void setEntityType(long entityType) {
		this.entityType = entityType;
	}

	public byte getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(byte currentMode) {
		this.currentMode = currentMode;
	}

	public long getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(long lastShootTime) {
		this.lastShootTime = lastShootTime;
	}

	public long getHitCounter() {
		return hitCounter;
	}

	public void setHitCounter(long hitCounter) {
		this.hitCounter = hitCounter;
	}

	public long getLastHitTime() {
		return lastHitTime;
	}

	public void setLastHitTime(long lastHitTime) {
		this.lastHitTime = lastHitTime;
	}

	public Appearance getApp() {
		return app;
	}

	public void setApp(Appearance app) {
		this.app = app;
	}

	public byte getFlags1() {
		return flags1;
	}

	public void setFlags1(byte flags1) {
		this.flags1 = flags1;
	}

	public byte getFlags2() {
		return flags2;
	}

	public void setFlags2(byte flags2) {
		this.flags2 = flags2;
	}

	public long getRollTime() {
		return rollTime;
	}

	public void setRollTime(long rollTime) {
		this.rollTime = rollTime;
	}

	public int getStunTime() {
		return stunTime;
	}

	public void setStunTime(int stunTime) {
		this.stunTime = stunTime;
	}

	public long getSlowedTime() {
		return slowedTime;
	}

	public void setSlowedTime(long slowedTime) {
		this.slowedTime = slowedTime;
	}

	public long getMakeBlueTime() {
		return makeBlueTime;
	}

	public void setMakeBlueTime(long makeBlueTime) {
		this.makeBlueTime = makeBlueTime;
	}

	public long getSpeedUpTime() {
		return speedUpTime;
	}

	public void setSpeedUpTime(long speedUpTime) {
		this.speedUpTime = speedUpTime;
	}

	public float getSlowPatchTime() {
		return slowPatchTime;
	}

	public void setSlowPatchTime(float slowPatchTime) {
		this.slowPatchTime = slowPatchTime;
	}

	public byte getClassType() {
		return classType;
	}

	public void setClassType(byte classType) {
		this.classType = classType;
	}

	public byte getSpecialization() {
		return specialization;
	}

	public void setSpecialization(byte specialization) {
		this.specialization = specialization;
	}

	public float getChargedMP() {
		return chargedMP;
	}

	public void setChargedMP(float chargedMP) {
		this.chargedMP = chargedMP;
	}

	public Vector3 getRayHit() {
		return rayHit;
	}

	public void setRayHit(Vector3 rayHit) {
		this.rayHit = rayHit;
	}

	public float getHP() {
		return HP;
	}

	public void setHP(float hP) {
		HP = hP;
	}

	public float getMP() {
		return MP;
	}

	public void setMP(float mP) {
		MP = mP;
	}

	public float getBlockPower() {
		return blockPower;
	}

	public void setBlockPower(float blockPower) {
		this.blockPower = blockPower;
	}

	public float getMaxHPMultiplier() {
		return maxHPMultiplier;
	}

	public void setMaxHPMultiplier(float maxHPMultiplier) {
		this.maxHPMultiplier = maxHPMultiplier;
	}

	public float getShootSpeed() {
		return shootSpeed;
	}

	public void setShootSpeed(float shootSpeed) {
		this.shootSpeed = shootSpeed;
	}

	public float getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(float damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
	}

	public float getArmorMultiplier() {
		return armorMultiplier;
	}

	public void setArmorMultiplier(float armorMultiplier) {
		this.armorMultiplier = armorMultiplier;
	}

	public float getResistanceMultiplier() {
		return resistanceMultiplier;
	}

	public void setResistanceMultiplier(float resistanceMultiplier) {
		this.resistanceMultiplier = resistanceMultiplier;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getCurrentXP() {
		return currentXP;
	}

	public void setCurrentXP(long currentXP) {
		this.currentXP = currentXP;
	}

	public Item getItemData() {
		return itemData;
	}

	public void setItemData(Item itemData) {
		this.itemData = itemData;
	}

	public Item[] getEquipment() {
		return equipment;
	}

	public void setEquipment(Item[] equipment) {
		this.equipment = equipment;
	}

	public long getIceBlockFour() {
		return iceBlockFour;
	}

	public void setIceBlockFour(long iceBlockFour) {
		this.iceBlockFour = iceBlockFour;
	}

	public long[] getSkills() {
		return skills;
	}

	public void setSkills(long[] skills) {
		this.skills = skills;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNa1() {
		return na1;
	}

	public void setNa1(long na1) {
		this.na1 = na1;
	}

	public long getNa2() {
		return na2;
	}

	public void setNa2(long na2) {
		this.na2 = na2;
	}

	public byte getNa3() {
		return na3;
	}

	public void setNa3(byte na3) {
		this.na3 = na3;
	}

	public long getNa4() {
		return na4;
	}

	public void setNa4(long na4) {
		this.na4 = na4;
	}

	public long getNa5() {
		return na5;
	}

	public void setNa5(long na5) {
		this.na5 = na5;
	}

	public long getNu1() {
		return nu1;
	}

	public void setNu1(long nu1) {
		this.nu1 = nu1;
	}

	public long getNu2() {
		return nu2;
	}

	public void setNu2(long nu2) {
		this.nu2 = nu2;
	}

	public long getNu3() {
		return nu3;
	}

	public void setNu3(long nu3) {
		this.nu3 = nu3;
	}

	public long getNu4() {
		return nu4;
	}

	public void setNu4(long nu4) {
		this.nu4 = nu4;
	}

	public long getNu5() {
		return nu5;
	}

	public void setNu5(long nu5) {
		this.nu5 = nu5;
	}

	public long getNu6() {
		return nu6;
	}

	public void setNu6(long nu6) {
		this.nu6 = nu6;
	}

	public byte getNu7() {
		return nu7;
	}

	public void setNu7(byte nu7) {
		this.nu7 = nu7;
	}

	public byte getNu8() {
		return nu8;
	}

	public void setNu8(byte nu8) {
		this.nu8 = nu8;
	}

	public long getParentOwner() {
		return parentOwner;
	}

	public void setParentOwner(long parentOwner) {
		this.parentOwner = parentOwner;
	}

	public long getNu11() {
		return nu11;
	}

	public void setNu11(long nu11) {
		this.nu11 = nu11;
	}

	public long getNu12() {
		return nu12;
	}

	public void setNu12(long nu12) {
		this.nu12 = nu12;
	}

	public long getNu13() {
		return nu13;
	}

	public void setNu13(long nu13) {
		this.nu13 = nu13;
	}

	public long getNu14() {
		return nu14;
	}

	public void setNu14(long nu14) {
		this.nu14 = nu14;
	}

	public long getNu15() {
		return nu15;
	}

	public void setNu15(long nu15) {
		this.nu15 = nu15;
	}

	public long getNu16() {
		return nu16;
	}

	public void setNu16(long nu16) {
		this.nu16 = nu16;
	}

	public long getNu17() {
		return nu17;
	}

	public void setNu17(long nu17) {
		this.nu17 = nu17;
	}

	public long getNu18() {
		return nu18;
	}

	public void setNu18(long nu18) {
		this.nu18 = nu18;
	}

	public long getNu20() {
		return nu20;
	}

	public void setNu20(long nu20) {
		this.nu20 = nu20;
	}

	public long getNu21() {
		return nu21;
	}

	public void setNu21(long nu21) {
		this.nu21 = nu21;
	}

	public long getNu22() {
		return nu22;
	}

	public void setNu22(long nu22) {
		this.nu22 = nu22;
	}

	public byte getNu19() {
		return nu19;
	}

	public void setNu19(byte nu19) {
		this.nu19 = nu19;
	}

	public int getDebugCap() {
		return debugCap;
	}

	public void setDebugCap(int debugCap) {
		this.debugCap = debugCap;
	}
}
