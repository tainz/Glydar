package org.glydar.glydar.protocol;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.paraglydar.models.BaseTarget;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.models.WorldTarget;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;

public abstract class Packet {

	private int id = -1;

	public int getID() {
		if (id < 0) {
			id = getClass().getAnnotation(PacketType.class).id();
		}
		return id;
	}

	public boolean getNoData() {
		return getClass().getAnnotation(PacketType.class).noData();
	}

	public void decode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be decoded");
	}

	public void encode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be encoded");
	}

	public void receivedFrom(GPlayer ply) {
		throw new IllegalAccessError("Packet cannot be received");
	}

	public void sendTo(GPlayer ply) {
		ply.getChannelContext().writeAndFlush(this);
	}

	public void sendTo(BaseTarget target) {
		for (Player ply : target.getPlayers()) {
			sendTo((GPlayer) ply);
		}
	}

	public void sendToWorld(World w) {
		sendTo(new WorldTarget(w));
	}

	private static final HashMap<Integer, Constructor<? extends Packet>> CUBE_WORLD_PACKET_HASH_MAP;

	private static void addPacketsFromPackage(String pkg) {
		Reflections refPackage = new Reflections(pkg);
		Set<Class<? extends Packet>> clientPackets = refPackage.getSubTypesOf(Packet.class);
		for (Class<? extends Packet> c : clientPackets) {
			try {
				PacketType a = c.getAnnotation(PacketType.class);
				if (a == null)
					continue;
				int classID = ((PacketType) a).id();

				CUBE_WORLD_PACKET_HASH_MAP.put(classID, c.getConstructor());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static {
		CUBE_WORLD_PACKET_HASH_MAP = new HashMap<Integer, Constructor<? extends Packet>>();
		addPacketsFromPackage("org.glydar.glydar.netty.packet.client");
		addPacketsFromPackage("org.glydar.glydar.netty.packet.shared");
	}

	public static Packet getByID(int id) {
		try {
			return CUBE_WORLD_PACKET_HASH_MAP.get(id).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
