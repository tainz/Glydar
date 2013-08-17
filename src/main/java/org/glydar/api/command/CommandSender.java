package org.glydar.api.command;

import org.glydar.api.permissions.Permissible;

/**
 * @author YoshiGenius
 */
public interface CommandSender extends Permissible {

    public String getName();

    public void sendMessage(String message);
    
}