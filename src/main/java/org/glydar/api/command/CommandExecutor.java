package org.glydar.api.command;

/**
 * @author YoshiGenius
 */
public interface CommandExecutor {
    
    public static enum CommandOutcome {
        SUCCESS, NO_PERMISSION, WRONG_USAGE, ERROR, NOT_HANDLED, FAILURE_OTHER;
    }
    
    public CommandOutcome execute(CommandSender cs, Command cmd, String lbl, String[] args);

}