package org.glydar.api;

import java.util.Collection;
import java.util.logging.Logger;

import org.glydar.api.permissions.Permission;
import org.glydar.glydar.models.GPlayer;

public interface Server {

	public Collection<GPlayer> getConnectedPlayers();

	public Logger getLogger();

	public boolean isRunning();

    public void shutdown();
    
    public void broadcastMessage(String message);
    
    public void broadcast(String message, String permission);

    public void broadcast(String message, Permission permission);
}
