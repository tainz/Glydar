package org.glydar.paraglydar.models;

import io.netty.channel.ChannelHandlerContext;
import org.glydar.paraglydar.netty.packet.CubeWorldPacket;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Player extends Entity implements BaseTarget {
    private static HashMap<Long, Player> connectedPlayers = new HashMap<Long, Player>();

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
	public Collection<Player> getPlayers() {
		Collection<Player> ret = new HashSet<Player>();
		ret.add(this);
		return ret;
	}

    public static Collection<Player> getConnectedPlayers() {
        return connectedPlayers.values();
    }

	public void playerJoined() {
		if(!connectedPlayers.containsKey(entityID))
			connectedPlayers.put(entityID, this);
	}

	public void playerLeft() {
		connectedPlayers.remove(entityID);
	}
}
