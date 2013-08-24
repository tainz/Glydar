package org.glydar.glydar.netty.packet;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.BaseTarget;
import org.glydar.glydar.models.EveryoneTarget;
import org.glydar.glydar.models.GPlayer;
import org.reflections.Reflections;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;
import org.glydar.api.event.events.PacketEvent;
import org.glydar.api.models.Player;

public abstract class CubeWorldPacket {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Packet {
        int id();
        boolean variableLength() default false;
        boolean noData() default false;
    }

	protected boolean doCacheIncoming() {
		return false;
	}

	private int _idCache = -1;
	public int getID() {
		if(_idCache < 0) {
			_idCache = getClass().getAnnotation(CubeWorldPacket.Packet.class).id();
		}
		return _idCache;
	}
	
	public boolean getNoData() {
		return getClass().getAnnotation(CubeWorldPacket.Packet.class).noData();
	}

	private ByteBuf bufCache = null;

	public void decode(ByteBuf buf) {
        //THIS IS BROKEN :C
        /*
		if(doCacheIncoming()) {
			int idx = buf.readerIndex();
			internalDecode(buf);
			int newIdx = buf.readerIndex();
			bufCache = new SlicedByteBuf(buf, idx, newIdx - idx);
		} else {
			internalDecode(buf);
		} */
        internalDecode(buf);
	}

	public void encode(ByteBuf buf) {
        //THIS IS BROKEN :C
        /*
		if(bufCache == null) {
			int idx = buf.writerIndex();
			internalEncode(buf);
			int newIdx = buf.writerIndex();
			bufCache = new SlicedByteBuf(buf, idx, newIdx - idx);
		} else {
			bufCache.readerIndex(0);
			buf.writeBytes(bufCache);
		} */
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
		bufCache = null;
		_sendTo(ply);
	}

    private void _sendTo(GPlayer ply) {
                PacketEvent evt = new PacketEvent(this);
                if (!(Glydar.getEventManager().callEvent(evt)).isCancelled()) {
                    ply.getChannelContext().write(this);
                }
    }

    public void sendTo(BaseTarget target) {
		bufCache = null;
        for (Player ply : target.getPlayers()) {
            _sendTo((GPlayer) ply);
        }
    }

	public void sendToAll() {
		sendTo(EveryoneTarget.INSTANCE);
	}

	private static final HashMap<Integer, Constructor<? extends CubeWorldPacket>> CUBE_WORLD_PACKET_HASH_MAP;

	private static void __addPacketsFromPackage(String pkg) {
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
		__addPacketsFromPackage("org.glydar.glydar.netty.packet.client");
		__addPacketsFromPackage("org.glydar.glydar.netty.packet.shared");
	}

    public static CubeWorldPacket getByID(int id) {
		try {
			return CUBE_WORLD_PACKET_HASH_MAP.get(id).newInstance();
		} catch (Exception e) {
            System.out.println("ID "+id);
			e.printStackTrace();
			return null;
		}
    }
}
