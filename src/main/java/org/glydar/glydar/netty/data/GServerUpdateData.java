package org.glydar.glydar.netty.data;

import org.glydar.glydar.netty.data.actions.GDamageAction;
import org.glydar.glydar.netty.data.actions.GKillAction;
import org.glydar.glydar.netty.data.actions.GPickupAction;
import org.glydar.glydar.netty.data.actions.GSoundAction;
import org.glydar.glydar.netty.packet.client.Packet13MissionData;
import org.glydar.glydar.netty.packet.client.Packet7HitNPC;
import org.glydar.glydar.netty.packet.client.Packet9ShootArrow;

import io.netty.buffer.ByteBuf;

public class GServerUpdateData implements BaseData {
	GPacket4UnknownData[] unknownArray1;
	Packet7HitNPC[] hitPackets;
	byte[][] unknownArray2;
	GSoundAction[] soundActions;
	Packet9ShootArrow[] shootArrowPackets;
	byte[][] unknownArray3;
	GChunkItems[] chunkItems;
	UnknownArray4[] unknownArray4;
	GPickupAction[] pickupActions;
	GKillAction[] killActions;
	GDamageAction[] damageActions;
	byte[][] unknownArray5;
	Packet13MissionData[] missionData;
	
	@Override
	public void decode(ByteBuf buf) {
		int lengthI;
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray1[i] = new GPacket4UnknownData();
			unknownArray1[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			hitPackets[i] = new Packet7HitNPC();
			hitPackets[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray2[i] = new byte[72];
			buf.readBytes(unknownArray2[i]);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			soundActions[i] = new GSoundAction();
			soundActions[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			shootArrowPackets[i] = new Packet9ShootArrow();
			shootArrowPackets[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray3[i] = new byte[88];
			buf.readBytes(unknownArray3[i]);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			chunkItems[i] = new GChunkItems();
			chunkItems[i].decode(buf);
		}
		
		long lengthL = buf.readLong();
		for (long i = 0; i < lengthL; i++){
			unknownArray4[(int) i].u1 = buf.readLong();
			lengthI = buf.readInt();
			for (int j = 0; j< lengthI; j++){
				unknownArray4[(int) i].u2[j] = new byte[16];
				buf.readBytes(buf.readBytes(unknownArray4[(int) i].u2[j]));
			}
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			pickupActions[i] = new GPickupAction();
			pickupActions[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			killActions[i] = new GKillAction();
			killActions[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			damageActions[i] = new GDamageAction();
			damageActions[i].decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray5[i] = new byte[40];
			buf.readBytes(unknownArray5[i]);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			missionData[i] = new Packet13MissionData();
			missionData[i].decode(buf);
		}

	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(unknownArray1.length);
		for (GPacket4UnknownData u : unknownArray1){
			u.encode(buf);
		}
		
		buf.writeInt(hitPackets.length);
		for (Packet7HitNPC p : hitPackets){
			p.encode(buf);
		}
		
		buf.writeInt(unknownArray2.length);
		for (byte[] b : unknownArray2){
			buf.writeBytes(b);
		}

		buf.writeInt(soundActions.length);
		for (GSoundAction a : soundActions){
			a.encode(buf);
		}
		
		buf.writeInt(shootArrowPackets.length);
		for (Packet9ShootArrow p : shootArrowPackets){
			p.encode(buf);
		}
		
		buf.writeInt(unknownArray3.length);
		for (byte[] b : unknownArray3){
			buf.writeBytes(b);
		}
		
		buf.writeInt(chunkItems.length);
		for (GChunkItems c : chunkItems){
			c.encode(buf);
		}
		
		buf.writeLong(unknownArray4.length);
		for (UnknownArray4 u : unknownArray4){
			buf.writeLong(u.u1);
			buf.writeInt(u.u2.length);
			for (byte[] b : u.u2){
				buf.writeBytes(b);
			}
		}
		
		buf.writeInt(pickupActions.length);
		for (GPickupAction a : pickupActions){
			a.encode(buf);
		}
		
		buf.writeInt(killActions.length);
		for (GKillAction a : killActions){
			a.encode(buf);
		}
		
		buf.writeInt(damageActions.length);
		for (GDamageAction a : damageActions){
			a.encode(buf);
		}
		
		buf.writeInt(unknownArray5.length);
		for (byte[] b : unknownArray5){
			buf.writeBytes(b);
		}
		
		buf.writeInt(missionData.length);
		for (Packet13MissionData p : missionData){
			p.encode(buf);
		}
	}

	//TODO: Temporary, until we know what this is o.o
	protected class UnknownArray4 {
		long u1;
		byte[][] u2;
		
	}
}
