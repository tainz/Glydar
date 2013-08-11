package org.glydar.glydar.api.models;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.glydar.permissions.Permission;
import org.glydar.glydar.permissions.PermissionAttachment;

public interface Player extends Entity {

    /**
     * Temporary fix to allow plugins to manipulate entityData while we fix other issues.
     * Call this whenever you modify anything in Player.data and wish to update all of the clients.
     */
    public void forceUpdateData();
    
    public void forceUpdateData(GEntityData ed);
    
    public GEntityData getEntityData();

	public void setEntityData(GEntityData ed);
	
	public String getIp();
	
	public void sendMessageToPlayer(String message);
	
	public void kickPlayer(String message);
	
	public void kickPlayer();

    public boolean hasPermission(String permission);

    public boolean hasPermission(Permission permission);

    public List<PermissionAttachment> getAttachments();
    
    public void addAttachment(PermissionAttachment attachment);

    public boolean isAdmin();
    
    public void setAdmin(boolean admin);

    public String getName();
}
