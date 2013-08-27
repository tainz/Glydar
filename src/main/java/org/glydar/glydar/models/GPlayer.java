package org.glydar.glydar.models;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.paraglydar.command.CommandSender;
import org.glydar.paraglydar.models.BaseTarget;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.permissions.Permissible;
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
		channelCtx.disconnect();
		channelCtx.deregister();
	}

	public void kickPlayer() {
		sendMessage("You have been kicked!");
		playerLeft();
		channelCtx.disconnect();
		channelCtx.deregister();
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
