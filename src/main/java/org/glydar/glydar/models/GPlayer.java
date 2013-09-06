package org.glydar.glydar.models;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.protocol.client.Packet7HitNPC;
import org.glydar.glydar.protocol.data.GServerUpdateData;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.server.Packet15Seed;
import org.glydar.glydar.protocol.server.Packet4ServerUpdate;
import org.glydar.glydar.protocol.shared.Packet10Chat;
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

	public void sendPacket(Packet packet) {
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
		world.addEntity(entityID, this);
	}

	public void playerLeft() {
		Glydar.getServer().removeEntity(entityID);
		world.removeEntity(entityID);
		channelCtx.close();
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
	}

	public void kickPlayer() {
		kickPlayer("You have been kicked!");
	}
	
	@Override
	public void changeWorld(World w){
		super.changeWorld(w);
		new Packet15Seed(w.getSeed()).sendTo(this);
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

	private void damage(float damage, boolean critical){
		Packet7HitNPC hit = new Packet7HitNPC();
		hit.setId(1L);
		hit.setTargetId(entityID);
		hit.setDamage(damage);
		hit.setCritical((byte) 1);
		hit.setPosition(getEntityData().getPosition());
		hit.setHitDirection(getEntityData().getExtraVel());
		
		Packet4ServerUpdate s = new Packet4ServerUpdate();
		s.sud = new GServerUpdateData();
		s.sud.hitPackets.add(hit);
		s.sendTo(this);
	}
	
	@Override
	public void damage(float damage) {
		damage(damage, true);
	}

	@Override
	public void kill() {
		damage(getEntityData().getHP() + 1);
	}

	@Override
	public void heal(float health) {
		damage(-health);	
	}
	
	@Override
	public void setHealth(float health) {
		damage(health - getEntityData().getHP(), false);
	}

	public void stun(int seconds) {
		Packet7HitNPC stun = new Packet7HitNPC();
		stun.setId(1L);
		stun.setTargetId(entityID);
		stun.setCritical((byte) 1);
		stun.setStunDuration(seconds);
		stun.setPosition(getEntityData().getPosition());
		stun.setHitDirection(getEntityData().getExtraVel());
		
		Packet4ServerUpdate s = new Packet4ServerUpdate();
		s.sud = new GServerUpdateData();
		s.sud.hitPackets.add(stun);
		s.sendTo(this);
	}
	
	
}
