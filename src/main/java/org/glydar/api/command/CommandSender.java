package org.glydar.glydar.command;

import org.glydar.glydar.permissions.Permissible;

/**
 * @author YoshiGenius
 */
public interface CommandSender extends Permissible {

    public String getName();
    
}