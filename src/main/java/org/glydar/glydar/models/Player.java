package org.glydar.glydar.models;

import io.netty.channel.ChannelHandlerContext;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.data.EntityData;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Player extends Entity implements BaseTarget {
    private static HashMap<Long, Player> connectedPlayers = new HashMap<Long, Player>();

    public boolean joined = false;
    public EntityData data;
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
		if(!connectedPlayers.containsKey(entityID)) {
			connectedPlayers.put(entityID, this);
            this.joined = true;
        }
	}

	public void playerLeft() {
		connectedPlayers.remove(entityID);
        //Generate packet 0 with data
	}

    /**
     * Temporary fix to allow plugins to manipulate entityData while we fix other issues.
     * Call this whenever you modify anything in Player.data and wish to update all of the clients.
     */
    public void forceUpdateData() {
        new Packet0EntityUpdate(this.data).sendToAll();
    }

    public static Player getPlayerByEntityID(long id) {
        if(connectedPlayers.containsKey(id))
            return connectedPlayers.get(id);
        else
        {
            Glydar.getServer().getLogger().warning("Unable to find player with entity ID "+id+"! Returning null!");
            return null;
        }
    }
}
