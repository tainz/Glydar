package org.glydar.glydar.protocol.data;

import java.util.List;
import java.util.ArrayList;

import org.glydar.glydar.protocol.client.Packet13MissionData;
import org.glydar.glydar.protocol.client.Packet7Hit;
import org.glydar.glydar.protocol.client.Packet9Shoot;
import org.glydar.glydar.protocol.data.actions.GDamageAction;
import org.glydar.glydar.protocol.data.actions.GKillAction;
import org.glydar.glydar.protocol.data.actions.GPickupAction;
import org.glydar.glydar.protocol.data.actions.GSoundAction;

import io.netty.buffer.ByteBuf;

public class GWorldUpdateData implements BaseData {

	private final List<GPacket4UnknownData> unknownArray1;
	private final List<Packet7Hit> hitPackets;
	private final List<byte[]> unknownArray2;
	private final List<GSoundAction> soundActions;
	private final List<Packet9Shoot> shootPackets;
	private final List<byte[]> unknownArray3;
	private final List<GChunkItems> chunkItems;
	private final List<UnknownArray4> unknownArray4;
	private final List<GPickupAction> pickupActions;
	private final List<GKillAction> killActions;
	private final List<GDamageAction> damageActions;
	private final List<byte[]> unknownArray5;
	private final List<Packet13MissionData> missionData;

	private boolean changes;

	public GWorldUpdateData() {
		unknownArray1 = new ArrayList<>();
		unknownArray2 = new ArrayList<>();
		hitPackets = new ArrayList<>();
		soundActions = new ArrayList<>();
		shootPackets = new ArrayList<>();
		unknownArray3 = new ArrayList<>();
		chunkItems = new ArrayList<>();
		unknownArray4 = new ArrayList<>();
		pickupActions = new ArrayList<>();
		killActions = new ArrayList<>();
		damageActions = new ArrayList<>();
		unknownArray5 = new ArrayList<>();
		missionData = new ArrayList<>();

		changes = false;
	}

	@Override
	public void decode(ByteBuf buf) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeInt(unknownArray1.size());
		for (GPacket4UnknownData u : unknownArray1) {
			u.encode(buf);
		}

		buf.writeInt(hitPackets.size());
		for (Packet7Hit p : hitPackets) {
			p.encode(buf);
		}

		buf.writeInt(unknownArray2.size());
		for (byte[] b : unknownArray2) {
			buf.writeBytes(b);
		}

		buf.writeInt(soundActions.size());
		for (GSoundAction a : soundActions) {
			a.encode(buf);
		}

		buf.writeInt(shootPackets.size());
		for (Packet9Shoot p : shootPackets) {
			p.encode(buf);
		}

		buf.writeInt(unknownArray3.size());
		for (byte[] b : unknownArray3) {
			buf.writeBytes(b);
		}

		buf.writeInt(chunkItems.size());
		for (GChunkItems c : chunkItems) {
			c.encode(buf);
		}

		buf.writeInt(unknownArray4.size());
		for (UnknownArray4 u : unknownArray4) {
			buf.writeLong(u.u1);
			buf.writeInt(u.u2.size());
			for (byte[] b : u.u2) {
				buf.writeBytes(b);
			}
		}

		buf.writeInt(pickupActions.size());
		for (GPickupAction a : pickupActions) {
			a.encode(buf);
		}

		buf.writeInt(killActions.size());
		for (GKillAction a : killActions) {
			a.encode(buf);
		}

		buf.writeInt(damageActions.size());
		for (GDamageAction a : damageActions) {
			a.encode(buf);
		}

		buf.writeInt(unknownArray5.size());
		for (byte[] b : unknownArray5) {
			buf.writeBytes(b);
		}

		buf.writeInt(missionData.size());
		for (Packet13MissionData p : missionData) {
			p.encode(buf);
		}
	}

	public boolean hasChanges() {
		return changes;
	}

	public void pushShoot(Packet9Shoot shootPacket) {
		shootPackets.add(shootPacket);
		changes = true;
	}

	public void pushKill(GKillAction killAction) {
		killActions.add(killAction);
		changes = true;
	}

	public void pushHit(Packet7Hit hitPacket) {
		hitPackets.add(hitPacket);
		changes = true;
	}
}

//TODO: Temporary, until we know what this is o.o
class UnknownArray4 {
	long u1;
	ArrayList<byte[]> u2 = new ArrayList<byte[]>();
}
