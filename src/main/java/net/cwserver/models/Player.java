package net.cwserver.models;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashSet;
import java.util.Set;

public class Player implements BaseTarget {
	public final long entityID = Entity.getEntityID();
    private ChannelHandlerContext channelCtx;

    public void setChannelContext(ChannelHandlerContext ctx) {
        this.channelCtx = ctx;
    }

    public ChannelHandlerContext getChannelContext() {
        return this.channelCtx;
    }

	@Override
	public Set<Player> getPlayers() {
		Set<Player> ret = new HashSet<Player>();
		ret.add(this);
		return ret;
	}
}
