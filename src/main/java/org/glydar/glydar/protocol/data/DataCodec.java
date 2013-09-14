package org.glydar.glydar.protocol.data;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.Item;
import org.glydar.paraglydar.data.ItemUpgrade;
import org.glydar.paraglydar.data.Item;
import org.glydar.paraglydar.data.ItemUpgrade;
import org.glydar.paraglydar.geom.FloatVector3;
import org.glydar.paraglydar.geom.LongVector3;
import org.glydar.paraglydar.geom.Orientation;

import com.google.common.base.Charsets;

public final class DataCodec {

	public static EntityData readEntityData(ByteBuf buf, EntityData e){
		e.setId(buf.readLong());
		
		byte[] bitSetBuf = new byte[8];
		buf.readBytes(bitSetBuf);
		e.setBitSet(BitSet.valueOf(bitSetBuf));
		BitSet bitSet = e.getBitSet();

		if (bitSet.get(0)) {
			e.setPosition(readLongVector3(buf));
		}
		if (bitSet.get(1)) {
			e.setOrientation(readOrientation(buf));
		}
		if (bitSet.get(2)) {
			e.setVelocity(readFloatVector3(buf));
		}
		if (bitSet.get(3)) {
			e.setAccel(readFloatVector3(buf));
		}
		if (bitSet.get(4)) {
			e.setExtraVel(readFloatVector3(buf));
		}
		if (bitSet.get(5)) {
			e.setLookPitch(buf.readFloat());
		}
		if (bitSet.get(6)) {
			e.setPhysicsFlags(buf.readUnsignedInt());
		}
		if (bitSet.get(7)) {
			e.setHostileType(buf.readByte());
		}
		if (bitSet.get(8)) {
			e.setEntityType(buf.readUnsignedInt());
		}
		if (bitSet.get(9)) {
			e.setCurrentMode(buf.readByte());
		}
		if (bitSet.get(10)) {
			e.setLastShootTime(buf.readUnsignedInt());
		}
		if (bitSet.get(11)) {
			e.setHitCounter(buf.readUnsignedInt());
		}
		if (bitSet.get(12)) {
			e.setLastHitTime(buf.readUnsignedInt());
		}
		if (bitSet.get(13)) {
			e.setApp(readAppearance(buf));
		}
		if (bitSet.get(14)) {
			e.setFlags1(buf.readByte());
			e.setFlags2(buf.readByte());
		}
		if (bitSet.get(15)) {
			e.setRollTime(buf.readUnsignedInt());
		}
		if (bitSet.get(16)) {
			e.setStunTime(buf.readInt());
		}
		if (bitSet.get(17)) {
			e.setSlowedTime(buf.readUnsignedInt());
		}
		if (bitSet.get(18)) {
			e.setMakeBlueTime(buf.readUnsignedInt());
		}
		if (bitSet.get(19)) {
			e.setSpeedUpTime(buf.readUnsignedInt());
		}
		if (bitSet.get(20)) {
			e.setSlowPatchTime(buf.readFloat());
		}
		if (bitSet.get(21)) {
			e.setClassType(buf.readByte());
		}
		if (bitSet.get(22)) {
			e.setSpecialization(buf.readByte());
		}
		if (bitSet.get(23)) {
			e.setChargedMP(buf.readFloat());
		}
		if (bitSet.get(24)) {
			e.setNu1(buf.readUnsignedInt());
			e.setNu2(buf.readUnsignedInt());
			e.setNu3(buf.readUnsignedInt());
		}
		if (bitSet.get(25)) {
			e.setNu4(buf.readUnsignedInt());
			e.setNu5(buf.readUnsignedInt());
			e.setNu6(buf.readUnsignedInt());
		}
		if (bitSet.get(26)) {
			e.setRayHit(readFloatVector3(buf));
		}
		if (bitSet.get(27)) {
			e.setHP(buf.readFloat());
		}
		if (bitSet.get(28)) {
			e.setMP(buf.readFloat());
		}
		if (bitSet.get(29)) {
			e.setBlockPower(buf.readFloat());
		}
		if (bitSet.get(30)) {
			e.setMaxHPMultiplier(buf.readFloat());
			e.setShootSpeed(buf.readFloat());
			e.setDamageMultiplier(buf.readFloat());
			e.setArmorMultiplier(buf.readFloat());
			e.setResistanceMultiplier(buf.readFloat());
		}
		if (bitSet.get(31)) {
			e.setNu7(buf.readByte());
		}
		if (bitSet.get(32)) {
			e.setNu8(buf.readByte());
		}
		if (bitSet.get(33)) {
			e.setLevel(buf.readUnsignedInt());
		}
		if (bitSet.get(34)) {
			e.setCurrentXP(buf.readUnsignedInt());
		}
		if (bitSet.get(35)) {
			e.setParentOwner(buf.readLong());
		}
		if (bitSet.get(36)) {
			e.setNa1(buf.readUnsignedInt());
			e.setNa2(buf.readUnsignedInt());
		}
		if (bitSet.get(37)) {
			e.setNa3(buf.readByte());
		}
		if (bitSet.get(38)) {
			e.setNa4(buf.readUnsignedInt());
		}
		if (bitSet.get(39)) {
			e.setNa5(buf.readUnsignedInt());
			e.setNu11(buf.readUnsignedInt());
			e.setNu12(buf.readUnsignedInt());
		}
		if (bitSet.get(40)) {
			e.setSpawnPosition(readLongVector3(buf));
		}
		if (bitSet.get(41)) {
			e.setNu20(buf.readUnsignedInt());
			e.setNu21(buf.readUnsignedInt());
			e.setNu22(buf.readUnsignedInt());
		}
		if (bitSet.get(42)) {
			e.setNu19(buf.readByte());
		}
		if (bitSet.get(43)) {
			e.setItemData(readItem(buf));
		}
		if (bitSet.get(44)) {
			Item[] equipment = e.getEquipment();
			for (int i = 0; i < 13; i++) {
				equipment[i] = readItem(buf);
			}
			e.setEquipment(equipment);
		}
		if (bitSet.get(45)) {
			e.setName(new String(buf.readBytes(16).array(), Charsets.US_ASCII).trim());
		}
		if (bitSet.get(46)) {
			long[] skills = e.getSkills();
			for (int i = 0; i < 11; i++) {
				skills[i] = buf.readUnsignedInt();
			}
			e.setSkills(skills);
		}
		if (bitSet.get(47)) {
			e.setIceBlockFour(buf.readUnsignedInt());
		}

		e.setDebugCap(buf.capacity());
		buf.resetReaderIndex();
		buf.resetWriterIndex();
		
		return e;
	}
	
	public static void writeEntityData(ByteBuf buf, EntityData e){
		//For testing purposes using default dummy entity :)
		/*boolean dummy = false;
		if (name.contains("dummy")) dummy = true;*/
		
		buf.writeLong(e.getId()); //Ulong but whatever
		BitSet bitSet = e.getBitSet();
		buf.writeBytes(bitSet.toByteArray());
		buf.writeBytes(new byte[8 - bitSet.toByteArray().length]); //BitSet/BitArray are the stupidest classes ever :(
		if (bitSet.get(0)) {
			writeLongVector3(buf, e.getPosition());
		}
		if (bitSet.get(1)) {
			writeOrientation(buf, e.getOrientation());
		}
		if (bitSet.get(2)) {
			writeFloatVector3(buf, e.getVelocity());
		}
		if (bitSet.get(3)) {
			writeFloatVector3(buf, e.getAccel());
		}
		if (bitSet.get(4)) {
			writeFloatVector3(buf, e.getExtraVel());
		}
		if (bitSet.get(5)) {
			buf.writeFloat(e.getLookPitch());
		}
		if (bitSet.get(6)) {
			buf.writeInt((int) e.getPhysicsFlags());
		}
		if (bitSet.get(7)) {
			buf.writeByte(e.getHostileType());
		}
		if (bitSet.get(8)) {
			buf.writeInt((int) e.getEntityType());
		}
		if (bitSet.get(9)) {
			buf.writeByte(e.getCurrentMode());
		}
		if (bitSet.get(10)) {
			buf.writeInt((int) e.getLastShootTime());
		}
		if (bitSet.get(11)) {
			buf.writeInt((int) e.getHitCounter());
		}
		if (bitSet.get(12)) {
			buf.writeInt((int) e.getLastHitTime());
		}
		if (bitSet.get(13)) {
			writeAppearance(buf, e.getApp());
		}
		if (bitSet.get(14)) {
			buf.writeByte(e.getFlags1());
			buf.writeByte(e.getFlags2());
		}
		if (bitSet.get(15)) {
			buf.writeInt((int) e.getRollTime());
		}
		if (bitSet.get(16)) {
			buf.writeInt(e.getStunTime());
		}
		if (bitSet.get(17)) {
			buf.writeInt((int) e.getSlowedTime());
		}
		if (bitSet.get(18)) {
			buf.writeInt((int) e.getMakeBlueTime());
		}
		if (bitSet.get(19)) {
			buf.writeInt((int) e.getSpeedUpTime());
		}
		if (bitSet.get(20)) {
			buf.writeFloat(e.getSlowPatchTime());
		}
		if (bitSet.get(21)) {
			buf.writeByte(e.getClassType());
		}
		if (bitSet.get(22)) {
			buf.writeByte(e.getSpecialization());
		}
		if (bitSet.get(23)) {
			buf.writeFloat(e.getChargedMP());
		}
		if (bitSet.get(24)) {
			buf.writeInt((int) e.getNu1());
			buf.writeInt((int) e.getNu2());
			buf.writeInt((int) e.getNu3());
		}
		if (bitSet.get(25)) {
			buf.writeInt((int) e.getNu4());
			buf.writeInt((int) e.getNu5());
			buf.writeInt((int) e.getNu6());
		}
		if (bitSet.get(26)) {
			writeFloatVector3(buf, e.getRayHit());
		}
		if (bitSet.get(27)) {
			buf.writeFloat(e.getHP());
		}
		if (bitSet.get(28)) {
			buf.writeFloat(e.getMP());
		}
		if (bitSet.get(29)) {
			buf.writeFloat(e.getBlockPower());
		}
		if (bitSet.get(30)) {
			buf.writeFloat(e.getMaxHPMultiplier());
			buf.writeFloat(e.getShootSpeed());
			buf.writeFloat(e.getDamageMultiplier());
			buf.writeFloat(e.getArmorMultiplier());
			buf.writeFloat(e.getResistanceMultiplier());
		}
		if (bitSet.get(31)) {
			buf.writeByte(e.getNu7());
		}
		if (bitSet.get(32)) {
			buf.writeByte(e.getNu8());
		}
		if (bitSet.get(33)) {
			buf.writeInt((int) e.getLevel());
		}
		if (bitSet.get(34)) {
			buf.writeInt((int) e.getCurrentXP());
		}
		if (bitSet.get(35)) {
			buf.writeLong(e.getParentOwner());
		}
		if (bitSet.get(36)) {
			buf.writeInt((int) e.getNa1());
			buf.writeInt((int) e.getNa2());
		}
		if (bitSet.get(37)) {
			buf.writeByte(e.getNa3());
		}
		if (bitSet.get(38)) {
			buf.writeInt((int) e.getNa4());
		}
		if (bitSet.get(39)) {
			buf.writeInt((int) e.getNa5());
			buf.writeInt((int) e.getNu11());
			buf.writeInt((int) e.getNu12());
		}
		if (bitSet.get(40)) {
			writeLongVector3(buf, e.getSpawnPosition());
		}
		if (bitSet.get(41)) {
			buf.writeInt((int) e.getNu20());
			buf.writeInt((int) e.getNu21());
			buf.writeInt((int) e.getNu22());
		}
		if (bitSet.get(42)) {
			buf.writeByte(e.getNu19());
		}
		if (bitSet.get(43)) {
			writeItem(buf, e.getItemData());
		}
		if (bitSet.get(44)) {
			Item[] equipment = e.getEquipment();
			for (int i = 0; i < 13; i++) {
				writeItem(buf, equipment[i]);
			}
		}
		if (bitSet.get(45)) {
			byte[] ascii = e.getName().getBytes(Charsets.US_ASCII);
			buf.writeBytes(ascii);
			buf.writeBytes(new byte[16 - e.getName().length()]);
		}
		if (bitSet.get(46)) {
			long[] skills = e.getSkills();
			for (int i = 0; i < 11; i++) {
				buf.writeInt((int) skills[i]);
			}
		}
		if (bitSet.get(47)) {
			buf.writeInt((int) e.getIceBlockFour());
		}
		
		buf.capacity(buf.writerIndex() + 1);
		if (buf.readerIndex() > 0) {
			Glydar.getServer().getLogger().warning("Data read during encode.");
		}
	}
	
	public static Appearance readAppearance(ByteBuf buf) {
		Appearance a = new Appearance();
		a.setNotUsed1(buf.readByte());
		a.setNotUsed2(buf.readByte());
		a.setHairR(buf.readByte());
		a.setHairG(buf.readByte());
		a.setHairB(buf.readByte());
		buf.readByte(); //Skip
		a.setMovementFlags(buf.readByte());
		a.setEntityFlags(buf.readByte());
		a.setScale(buf.readFloat());
		a.setBoundingRadius(buf.readFloat());
		a.setBoundingHeight(buf.readFloat());
		a.setHeadModel(buf.readUnsignedShort());
		a.setHairModel(buf.readUnsignedShort());
		a.setHandModel(buf.readUnsignedShort());
		a.setFootModel(buf.readUnsignedShort());
		a.setBodyModel(buf.readUnsignedShort());
		a.setBackModel(buf.readUnsignedShort());
		a.setShoulderModel(buf.readUnsignedShort());
		a.setWingModel(buf.readUnsignedShort());
		a.setHeadScale(buf.readFloat());
		a.setBodyScale(buf.readFloat());
		a.setHandScale(buf.readFloat());
		a.setFootScale(buf.readFloat());
		a.setShoulderScale(buf.readFloat());
		a.setWeaponScale(buf.readFloat());
		a.setBackScale(buf.readFloat());
		a.setUnknown(buf.readFloat());
		a.setWingScale(buf.readFloat());
		a.setBodyPitch(buf.readFloat());
		a.setArmPitch(buf.readFloat());
		a.setArmRoll(buf.readFloat());
		a.setArmYaw(buf.readFloat());
		a.setFeetPitch(buf.readFloat());
		a.setWingPitch(buf.readFloat());
		a.setBackPitch(buf.readFloat());
		a.setBodyOffset(readFloatVector3(buf));
		a.setHeadOffset(readFloatVector3(buf));
		a.setHandOffset(readFloatVector3(buf));
		a.setFootOffset(readFloatVector3(buf));
		a.setBackOffset(readFloatVector3(buf));
		a.setWingOffset(readFloatVector3(buf));
		return a;
	}
	
	public static void writeAppearance(ByteBuf buf, Appearance a){
		buf.writeByte(a.getNotUsed1());
		buf.writeByte(a.getNotUsed2());
		buf.writeByte(a.getHairR());
		buf.writeByte(a.getHairG());
		buf.writeByte(a.getHairB());
		buf.writeBytes(new byte[1]);
		buf.writeByte(a.getMovementFlags());
		buf.writeByte(a.getEntityFlags());
		buf.writeFloat(a.getScale());
		buf.writeFloat(a.getBoundingRadius());
		buf.writeFloat(a.getBoundingHeight());
		buf.writeShort(a.getHeadModel());
		buf.writeShort(a.getHairModel());
		buf.writeShort(a.getHandModel());
		buf.writeShort(a.getFootModel());
		buf.writeShort(a.getBodyModel());
		buf.writeShort(a.getBackModel());
		buf.writeShort(a.getShoulderModel());
		buf.writeShort(a.getWingModel());
		buf.writeFloat(a.getHeadScale());
		buf.writeFloat(a.getBodyScale());
		buf.writeFloat(a.getHandScale());
		buf.writeFloat(a.getFootScale());
		buf.writeFloat(a.getShoulderScale());
		buf.writeFloat(a.getWeaponScale());
		buf.writeFloat(a.getBackScale());
		buf.writeFloat(a.getUnknown());
		buf.writeFloat(a.getWingScale());
		buf.writeFloat(a.getBodyPitch());
		buf.writeFloat(a.getArmPitch());
		buf.writeFloat(a.getArmRoll());
		buf.writeFloat(a.getArmYaw());
		buf.writeFloat(a.getFeetPitch());
		buf.writeFloat(a.getWingPitch());
		buf.writeFloat(a.getBackPitch());
		writeFloatVector3(buf, a.getBodyOffset());
		writeFloatVector3(buf, a.getHeadOffset());
		writeFloatVector3(buf, a.getHandOffset());
		writeFloatVector3(buf, a.getFootOffset());
		writeFloatVector3(buf, a.getBackOffset());
		writeFloatVector3(buf, a.getWingOffset());
	}
	
	public static Item readItem(ByteBuf buf){
		Item i = new Item();
		i.setType(buf.readByte());
		i.setSubtype(buf.readByte());
		buf.readBytes(2); //Skip
		i.setModifier(buf.readUnsignedInt());
		i.setMinusModifier(buf.readUnsignedInt());
		i.setRarity(buf.readByte());
		i.setMaterial(buf.readByte());
		i.setFlags(buf.readByte());
		buf.readByte();
		i.setLevel(buf.readShort());
		buf.readBytes(2); //Skip
		ItemUpgrade[] upgrades = i.getUpgrades();
		for (int j = 0; j < upgrades.length; ++j) {
			upgrades[j] = readItemUpgrade(buf);
		}
		i.setUpgrades(upgrades);
		
		i.setUpgradeCount(buf.readUnsignedInt());
		return i;
	}
	
	public static void writeItem(ByteBuf buf, Item i){
		buf.writeByte(i.getType());
		buf.writeByte(i.getSubtype());
		buf.writeBytes(new byte[2]);
		buf.writeInt((int) i.getModifier());
		buf.writeInt((int) i.getMinusModifier());
		buf.writeByte(i.getRarity());
		buf.writeByte(i.getMaterial());
		buf.writeByte(i.getFlags());
		buf.writeBytes(new byte[1]);
		buf.writeShort(i.getLevel());
		buf.writeBytes(new byte[2]);
		ItemUpgrade[] upgrades = i.getUpgrades();
		for (int j = 0; j < upgrades.length; ++j) {
			writeItemUpgrade(buf, upgrades[j]);
		}
		buf.writeInt((int) i.getUpgradeCount());
	}
	
	public static ItemUpgrade readItemUpgrade(ByteBuf buf) {
		ItemUpgrade u = new ItemUpgrade();
		u.setxOffset(buf.readByte());
		u.setyOffset(buf.readByte());
		u.setzOffset(buf.readByte());
		u.setMaterial(buf.readByte());
		u.setLevel(buf.readUnsignedInt());
		return u;
	}
	
	public static void writeItemUpgrade(ByteBuf buf, ItemUpgrade u) {
		buf.writeByte(u.getxOffset());
		buf.writeByte(u.getyOffset());
		buf.writeByte(u.getzOffset());
		buf.writeByte(u.getMaterial());
		buf.writeInt((int) u.getLevel());
	}
	
	public static FloatVector3 readFloatVector3(ByteBuf buf) {
		return new FloatVector3(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public static LongVector3 readLongVector3(ByteBuf buf) {
		return new LongVector3(buf.readLong(), buf.readLong(), buf.readLong());
	}

	public static Orientation readOrientation(ByteBuf buf) {
		return new Orientation(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public static void writeFloatVector3(ByteBuf buf, FloatVector3 vector) {
		buf.writeFloat(vector.getX());
		buf.writeFloat(vector.getY());
		buf.writeFloat(vector.getZ());
	}

	public static void writeLongVector3(ByteBuf buf, LongVector3 vector) {
		buf.writeLong(vector.getX());
		buf.writeLong(vector.getY());
		buf.writeLong(vector.getZ());
	}

	public static void writeOrientation(ByteBuf buf, Orientation orientation) {
		buf.writeFloat(orientation.getRoll());
		buf.writeFloat(orientation.getPitch());
		buf.writeFloat(orientation.getYaw());
	}
}
