package org.glydar.api.command;

import org.glydar.api.permissions.Permission;

/**
 * @author YoshiGenius
 */
public class Command {
    private final Permission perm;
    private final String lbl;
    private final String usage;
    private String permmsg = "You don't have access to that command.";
    
    public Command(String lbl, Permission perm, String usage) {
        this.lbl = lbl;
        this.perm = perm;
        this.usage = usage;
    }
    
    public String getCommandName() {
        return this.lbl;
    }
    
    public Permission getPermission() {
        return this.perm;
    }
    
    public String getPermissionMessage() {
        return this.permmsg;
    }
    
    public void setPermissionMessage(String permmsg) {
        this.permmsg = permmsg;
    }
    
    public String getUsage() {
        return this.usage;
    }

}