package org.glydar.api.command;

import org.glydar.api.permissions.Permission;
import org.glydar.api.plugin.Plugin;

/**
 * @author YoshiGenius
 */
public class Command {
    private final Permission perm;
    private final String lbl;
    private String usage;
    private String permmsg = "You don't have access to that command.";
    private Plugin plugin;
    private String[] aliases = new String[]{};
    
    public Command(Plugin plugin, String lbl, Permission perm, String usage) {
        this.lbl = lbl;
        this.perm = perm;
        this.usage = usage;
        this.plugin = plugin;
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
    
    public Command setPermissionMessage(String permmsg) {
        this.permmsg = permmsg;
        return this;
    }
    
    public String getUsage() {
        return this.usage;
    }
    
    public Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
    
    public Command setPlugin(Plugin plg) {
        this.plugin = plg;
        return this;
    }
    
    public String[] getAliases() {
        return this.aliases;
    }
    
    public Command setAliases(String[] aliases) {
        if (aliases == null || aliases.length == 0) {
            return this;
        } else {
            this.aliases = aliases;
            return this;
        }
    }

}