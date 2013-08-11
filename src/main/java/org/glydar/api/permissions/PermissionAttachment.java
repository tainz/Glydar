package org.glydar.api.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.glydar.api.plugin.Plugin;

/**
 * @author YoshiGenius
 */
public class PermissionAttachment {
    
    private static HashMap<Permissible, List<PermissionAttachment>> attachments = new HashMap<Permissible, List<PermissionAttachment>>();
    
    public static List<PermissionAttachment> getAttachments(Permissible p) {
        if (attachments.get(p) == null) {
            return attachments.put(p, new ArrayList<PermissionAttachment>());
        }
        return attachments.get(p);
    }
    
    public static PermissionAttachment addAttachment(PermissionAttachment attachment) {
        if (getAttachments(attachment.p) == null) {
            attachments.put(attachment.p, new ArrayList<PermissionAttachment>());
        }
        if (attachments.get(attachment.p).contains(attachment)) {
            return attachment;
        } else {
            getAttachments(attachment.p).add(attachment);
            return attachment;
        }
    }
    
    private final Plugin owner;
    private final Permissible p;
    private List<Permission> permissions;

    public PermissionAttachment(Plugin owner, Permissible p, List<Permission> permissions) {
        this.owner = owner;
        this.p = p;
        this.permissions = permissions;
    }
    
    public Plugin getOwner() {
        return this.owner;
    }
    
    public Permissible getPermissible() {
        return this.p;
    }
    
    public List<Permission> getPermissions() {
        return this.permissions;
    }
    
    public boolean hasPermission(Permission permission) {
        if (permissions == null) {
            permissions = new ArrayList<Permission>();
            return false;
        }
        if (permission == null || permission.getPermission() == null || permission.getPermissionDefault() == null) {
            return false;
        }
        return permissions.contains(permission);
    }

}