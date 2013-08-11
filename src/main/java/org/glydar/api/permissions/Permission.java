package org.glydar.glydar.permissions;

/**
 * @author YoshiGenius
 */
public class Permission {
    
    public static enum PermissionDefault {
        TRUE, FALSE, ADMIN, NON_ADMIN;
    }
    
    private final String permission;
    private final PermissionDefault pdefault;
    
    public Permission(String permission, PermissionDefault pdefault) {
        this.permission = permission;
        this.pdefault = pdefault;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public PermissionDefault getPermissionDefault() {
        return this.pdefault;
    }

}