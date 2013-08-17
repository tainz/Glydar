package org.glydar.glydar;

import java.util.HashMap;
import org.glydar.api.command.Command;
import org.glydar.api.command.CommandExecutor;
import org.glydar.api.command.CommandSender;
import org.glydar.api.plugin.Plugin;

/**
 * @author YoshiGenius
 */
public final class GCommander {
    
    private static final HashMap<Command, CommandExecutor> executors = new HashMap<>();
    
    public static final String INVALID_COMMAND = "Invalid command entered! Type /help for help!";
    
    protected GCommander() {}
    
    protected static Plugin getOwningPlugin(String lbl) {
        for (Command cmd : executors.keySet()) {
            if (cmd.getCommandName().equalsIgnoreCase(lbl)) {
                return cmd.getPlugin();
            }
        }
        return null;
    }
    
    protected static boolean addCommand(Command cmd, CommandExecutor executor) {
        if (executors.containsKey(cmd) || executors.containsValue(executor)) {
            return false;
        }
        executors.put(cmd, executor);
        return true;
    }
    
    protected static Command getCommand(String lbl) {
        if (lbl == null) {
            return null;
        }
        for (Command cmd : executors.keySet()) {
            if (cmd.getCommandName().equalsIgnoreCase(lbl)) {
                return cmd;
            }
        }
        return null;
    }
    
    protected static CommandExecutor getExecutor(Command cmd) {
        if (cmd == null) {
            return null;
        }
        return executors.get(cmd);
    }
    
    public static boolean exec(CommandSender cs, String lbl, String[] arg) {
        Command cmd = getCommand(lbl);
        if (cmd == null) {
            cs.sendMessage(INVALID_COMMAND);
            return true;
        } else {
            CommandExecutor executor = getExecutor(cmd);
            if (executor == null) {
                cs.sendMessage(INVALID_COMMAND);
                return true;
            }
        }
    }

}