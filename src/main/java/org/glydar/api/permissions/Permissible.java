package org.glydar.api.permissions;

/**
 * @author YoshiGenius
 */
public interface Permissible {
    
    public boolean hasPermission(String permission);
    
    public boolean hasPermission(Permission permission);

}
