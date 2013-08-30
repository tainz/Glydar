package org.glydar.glydar.netty.packet;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.paraglydar.models.BaseTarget;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.models.WorldTarget;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;

public abstract class CubeWorldPacket {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Packet {
		int id();

		boolean variableLength() default false;

		boolean noData() default false;
	}

	private int id = -1;

	public int getID() {
		if (id < 0) {
			id = getClass().getAnnotation(CubeWorldPacket.Packet.class).id();
		}
		return id;
	}

	public boolean getNoData() {
		return getClass().getAnnotation(CubeWorldPacket.Packet.class).noData();
	}

	public void decode(ByteBuf buf) {
		internalDecode(buf);
	}

	public void encode(ByteBuf buf) {
		internalEncode(buf);
	}

	protected void internalDecode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be decoded");
	}

	public void receivedFrom(GPlayer ply) {
		throw new IllegalAccessError("Packet cannot be received");
	}

	protected void internalEncode(ByteBuf buf) {
		throw new IllegalAccessError("Packet cannot be encoded");
	}

	public void sendTo(GPlayer ply) {
		_sendTo(ply);
	}

	private void _sendTo(GPlayer ply) {
		ply.getChannelContext().writeAndFlush(this);
	}

	public void sendTo(BaseTarget target) {
		for (Player ply : target.getPlayers()) {
			_sendTo((GPlayer) ply);
		}
	}

	public void sendToWorld(World w) {
		sendTo(new WorldTarget(w));
	}

	private static final HashMap<Integer, Constructor<? extends CubeWorldPacket>> CUBE_WORLD_PACKET_HASH_MAP;

	private static void addPacketsFromPackage(String pkg) {
		Reflections refPackage = new Reflections(pkg);
		Set<Class<? extends CubeWorldPacket>> clientPackets = refPackage.getSubTypesOf(CubeWorldPacket.class);
		for (Class<? extends CubeWorldPacket> c : clientPackets) {
			try {
				Annotation a = c.getAnnotation(CubeWorldPacket.Packet.class);
				if (a == null)
					continue;
				int classID = ((CubeWorldPacket.Packet) a).id();

				CUBE_WORLD_PACKET_HASH_MAP.put(classID, c.getConstructor());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static {
		CUBE_WORLD_PACKET_HASH_MAP = new HashMap<Integer, Constructor<? extends CubeWorldPacket>>();
		addPacketsFromPackage("org.glydar.glydar.netty.packet.client");
		addPacketsFromPackage("org.glydar.glydar.netty.packet.shared");
	}

	public static CubeWorldPacket getByID(int id) {
		try {
			return CUBE_WORLD_PACKET_HASH_MAP.get(id).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
