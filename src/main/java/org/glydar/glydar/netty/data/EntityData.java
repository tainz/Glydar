package org.glydar.glydar.netty.data;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import org.glydar.glydar.Glydar;
import sun.security.util.BitArray;

/* Structures and data discovered by mat^2 (http://github.com/matpow2) */

public class EntityData implements BaseData {

    public long id;
    public byte[] bitmask;

    public long posX;
    public long posY;
    public long posZ;

    public float roll;
    public float pitch;
    public float yaw;

    public Vector3 velocity;

    public Vector3 accel;

    public Vector3 extraVel;

    public float lookPitch;
    public long physicsFlags; //Uint
    public byte speedFlags;
    public long entityType; //Uint
    public byte currentMode;
    public long lastShootTime; //Uint
    public long hitCounter; //Uint
    public long lastHitTime; //Uint
    public Appearance app;
    public byte flags1;
    public byte flags2;
    public long rollTime; //Uint
    public int stunTime;
    public long slowedTime; //Uint
    public long makeBlueTime; //Uint
    public long speedUpTime; //Uint
    public float slowPatchTime;
    public byte classType;
    public byte specialization;
    public float chargedMP;

    public Vector3 rayHit;

    public float HP;
    public float MP;

    public float blockPower;
    public float maxHPMultiplier;
    public float shootSpeed;
    public float damageMultiplier;
    public float armorMultiplier;
    public float resistanceMultiplier;
    public long level;  //Uint
    public long currentXP; //Uint
    public Item itemData;
    public Item[] equipment;

    public long iceBlockFour; //Uint
    public long[] skills;
    public String name;

    public long na1; //Uint
    public long na2; // |
    public byte na3;
    public long na4;
    public long na5;
    public long nu1;
    public long nu2;
    public long nu3;
    public long nu4;
    public long nu5;
    public long nu6;
    public byte nu7;
    public byte nu8;
    public long parentOwner;
    public long nu11;
    public long nu12;
    public long nu13;
    public long nu14;
    public long nu15;
    public long nu16;
    public long nu17;
    public long nu18;
    public long nu20;
    public long nu21;
    public long nu22;
    public byte nu19;

    public int debugCap;

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
    public void decode(ByteBuf buf) {
        id = buf.readLong();
        bitmask = buf.readBytes(8).array();
        BitArray bitArray = new BitArray(8*bitmask.length, bitmask); //Size in bits, byte[]

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

    }

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
    public void updateFrom(EntityData changes) {
        if(changes.id != this.id)
        {
            Glydar.getServer().getLogger().warning("Tried to update entity ID "+this.id+" with changes from ID "+changes.id+"!");
            return;
        }
        BitArray bitArray = new BitArray(8*changes.bitmask.length, changes.bitmask); //Size in bits, byte[]

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
}
