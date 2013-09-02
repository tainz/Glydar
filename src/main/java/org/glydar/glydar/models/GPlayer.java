package org.glydar.glydar.models;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.data.GVector3;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.server.Packet15Seed;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.permissions.Permission;
import org.glydar.paraglydar.permissions.PermissionAttachment;

public class GPlayer extends GEntity implements Player {
	public boolean joined = false;
	private ChannelHandlerContext channelCtx;
    private boolean admin;

    public GPlayer() {
		super();
	}

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


	public void playerJoined() {
		Glydar.getServer().addEntity(entityID, this);
		this.joined = true;
	}

	public void playerLeft() {
		Glydar.getServer().removeEntity(entityID);
		forceUpdateData();
	}

	public String getIp() {
		return ((InetSocketAddress) channelCtx.channel().remoteAddress()).getAddress().getHostAddress();
	}

	public void sendMessage(String message) {
		this.sendPacket(new Packet10Chat(message, 0));
	}

	public void sendMessageToPlayer(String message) {
		sendMessage(message);
	}

	public void kickPlayer(String message) {
		sendMessage(message);
		playerLeft();
		channelCtx.close();
	}

	public void kickPlayer() {
		sendMessage("You have been kicked!");
		playerLeft();
		channelCtx.close();
	}
	
	@Override
	public void changeWorld(World w){
		//Temporary(?) way of removing all current models in players client!
		for (Entity e : w.getWorldEntities()) {
			GEntityData ed = new GEntityData(e.getEntityData());
			ed.setHostileType((byte) 2);
			GVector3<Long> v = new GVector3<Long>();
			v.setX((long) 0);
			v.setY((long) 0);
			v.setZ((long) 0);
			ed.setPosition(v);
			new Packet0EntityUpdate(ed).sendTo(this);
		}
		super.changeWorld(w);
		new Packet15Seed(w.getSeed()).sendTo(this);
		for (Entity e : this.getWorld().getWorldEntities()) {
			if (((GEntity) e).entityID == this.entityID) {
				continue;
			}
			this.sendPacket(new Packet0EntityUpdate(e.getEntityData()));
		}
	}

	@Override
	public boolean hasPermission(String permission) {
		return hasPermission(new Permission(permission, Permission.PermissionDefault.FALSE));
	}

	@Override
	public boolean hasPermission(Permission permission) {
		if (getAttachments() == null || getAttachments().isEmpty()) {
			switch (permission.getPermissionDefault()) {
				case TRUE:
					return true;
				case FALSE:
					return false;
				case ADMIN:
					return isAdmin();
				case NON_ADMIN:
					return (!isAdmin());
			}
		}
		for (PermissionAttachment attachment : getAttachments()) {
			if (attachment.hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	public List<PermissionAttachment> getAttachments() {
		return PermissionAttachment.getAttachments(this);
	}

	public void addAttachment(PermissionAttachment attachment) {
		PermissionAttachment.addAttachment(attachment);
	}

	// TODO
	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String getName() {
		return this.data.getName();
	}
}
