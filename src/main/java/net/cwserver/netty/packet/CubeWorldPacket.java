package net.cwserver.netty.packet;

import io.netty.buffer.ByteBuf;
import net.cwserver.models.BaseTarget;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.shared.Packet0EntityUpdate;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public abstract class CubeWorldPacket {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Packet {
        int id();

        boolean variableLength() default false;
    }

    public void decode(ByteBuf buf) {
        throw new IllegalAccessError("Packet cannot be decoded");
    }

    public void receivedFrom(Player ply) {
        throw new IllegalAccessError("Packet cannot be received");
    }

    public void encode(ByteBuf buf) {
        throw new IllegalAccessError("Packet cannot be encoded");
    }

    public void sendTo(Player ply) {
        throw new IllegalAccessError("Packet cannot be sent");
    }

    public void sendTo(BaseTarget target) {
        for (Player ply : target.getPlayers()) {
            sendTo(ply);
        }
    }

    public static CubeWorldPacket getByID(int id) {
        Reflections clientPackage = new Reflections("net.cwserver.netty.packet.client");
        Set<Class<? extends CubeWorldPacket>> clientPackets = clientPackage.getSubTypesOf(CubeWorldPacket.class);
        for (Class<? extends CubeWorldPacket> c : clientPackets) {
            try {
                CubeWorldPacket p = c.newInstance();
                Annotation a = p.getClass().getAnnotation(CubeWorldPacket.Packet.class);
                if (a == null)
                    continue;
                int classID = ((CubeWorldPacket.Packet) a).id();
                if (classID == id) {
                    return p;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Unable to find class for packet ID " + id);
        return null;
    }
}
