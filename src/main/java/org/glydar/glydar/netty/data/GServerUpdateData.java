package org.glydar.glydar.netty.data;

import java.util.ArrayList;

import org.glydar.glydar.netty.data.actions.GDamageAction;
import org.glydar.glydar.netty.data.actions.GKillAction;
import org.glydar.glydar.netty.data.actions.GPickupAction;
import org.glydar.glydar.netty.data.actions.GSoundAction;
import org.glydar.glydar.netty.packet.client.Packet13MissionData;
import org.glydar.glydar.netty.packet.client.Packet7HitNPC;
import org.glydar.glydar.netty.packet.client.Packet9ShootProjectile;

import io.netty.buffer.ByteBuf;

//TODO: ArrayLists!
public class GServerUpdateData implements BaseData {
	ArrayList<GPacket4UnknownData> unknownArray1;
	public ArrayList<Packet7HitNPC> hitPackets;
	byte[][] unknownArray2;
	ArrayList<GSoundAction> soundActions;
	public ArrayList<Packet9ShootProjectile> shootPackets;
	byte[][] unknownArray3;
	ArrayList<GChunkItems> chunkItems;
	UnknownArray4[] unknownArray4;
	ArrayList<GPickupAction> pickupActions;
	ArrayList<GKillAction> killActions;
	ArrayList<GDamageAction> damageActions;
	byte[][] unknownArray5;
	ArrayList<Packet13MissionData> missionData;
	
	public GServerUpdateData() {
		unknownArray1 = new ArrayList<GPacket4UnknownData>();
		hitPackets = new ArrayList<Packet7HitNPC>();
		soundActions = new ArrayList<GSoundAction>();
		shootPackets = new ArrayList<Packet9ShootProjectile>();
		chunkItems = new ArrayList<GChunkItems>();
		pickupActions = new ArrayList<GPickupAction>();
		killActions = new ArrayList<GKillAction>();
		damageActions = new ArrayList<GDamageAction>();
		missionData = new ArrayList<Packet13MissionData>();
	}
	
	@Override
	public void decode(ByteBuf buf) {
		int lengthI;
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray1.set(i, new GPacket4UnknownData());
			unknownArray1.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			hitPackets.set(i, new Packet7HitNPC());
			hitPackets.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray2[i] = new byte[72];
			buf.readBytes(unknownArray2[i]);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			soundActions.set(i, new GSoundAction());
			soundActions.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			shootPackets.set(i, new Packet9ShootProjectile());
			shootPackets.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray3[i] = new byte[88];
			buf.readBytes(unknownArray3[i]);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			chunkItems.set(i, new GChunkItems());
			chunkItems.get(i).decode(buf);
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
			pickupActions.set(i, new GPickupAction());
			pickupActions.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			killActions.set(i, new GKillAction());
			killActions.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			damageActions.set(i, new GDamageAction());
			damageActions.get(i).decode(buf);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			unknownArray5[i] = new byte[40];
			buf.readBytes(unknownArray5[i]);
		}
		
		lengthI = buf.readInt();
		for (int i = 0; i < lengthI; i++){
			missionData.set(i, new Packet13MissionData());
			missionData.get(i).decode(buf);
		}

	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(unknownArray1.size());
		for (GPacket4UnknownData u : unknownArray1){
			u.encode(buf);
		}
		
		buf.writeInt(hitPackets.size());
		for (Packet7HitNPC p : hitPackets){
			p.encode(buf);
		}
		
		buf.writeInt(unknownArray2.length);
		for (byte[] b : unknownArray2){
			buf.writeBytes(b);
		}

		buf.writeInt(soundActions.size());
		for (GSoundAction a : soundActions){
			a.encode(buf);
		}
		
		buf.writeInt(shootPackets.size());
		for (Packet9ShootProjectile p : shootPackets){
			p.encode(buf);
		}
		
		buf.writeInt(unknownArray3.length);
		for (byte[] b : unknownArray3){
			buf.writeBytes(b);
		}
		
		buf.writeInt(chunkItems.size());
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
		
		buf.writeInt(pickupActions.size());
		for (GPickupAction a : pickupActions){
			a.encode(buf);
		}
		
		buf.writeInt(killActions.size());
		for (GKillAction a : killActions){
			a.encode(buf);
		}
		
		buf.writeInt(damageActions.size());
		for (GDamageAction a : damageActions){
			a.encode(buf);
		}
		
		buf.writeInt(unknownArray5.length);
		for (byte[] b : unknownArray5){
			buf.writeBytes(b);
		}
		
		buf.writeInt(missionData.size());
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
