package org.glydar.api.models;

import java.util.List;

import org.glydar.api.permissions.Permission;
import org.glydar.api.permissions.PermissionAttachment;

public interface Player extends Entity {

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
