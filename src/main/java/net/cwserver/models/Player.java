package net.cwserver.models;

import io.netty.channel.ChannelHandlerContext;
import net.cwserver.netty.packet.CubeWorldPacket;

import java.util.HashSet;
import java.util.Set;

public class Player implements BaseTarget {
	public final long entityID = Entity.getEntityID();
    private static Set<Player> connectedPlayers = new HashSet<Player>();

    private ChannelHandlerContext channelCtx;

    public void setChannelContext(ChannelHandlerContext ctx) {
        this.channelCtx = ctx;
    }

    public ChannelHandlerContext getChannelContext() {
        return this.channelCtx;
    }

	public void sendPacket(CubeWorldPacket packet) {
		packet.sendTo(this);
	}

	@Override
	public Set<Player> getPlayers() {
		Set<Player> ret = new HashSet<Player>();
		ret.add(this);
		return ret;
	}

    public static Set<Player> getConnectedPlayers() {
        return connectedPlayers;
    }
}
