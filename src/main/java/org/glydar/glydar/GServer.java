package org.glydar.glydar;

import org.glydar.api.Server;
import org.glydar.api.permissions.Permission;
import org.glydar.api.permissions.Permission.PermissionDefault;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.server.Packet4ServerUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.glydar.util.LogFormatter;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class GServer implements Runnable, Server {

	private final Logger LOGGER = Logger.getLogger(Glydar.class.getName());

    private boolean running = true;

    public final boolean DEBUG;
    
    public Packet4ServerUpdate serverUpdatePacket = new Packet4ServerUpdate();
    
    private HashMap<Long, GPlayer> connectedPlayers = new HashMap<Long, GPlayer>();

    public GServer(boolean debug) {
        this.DEBUG = debug;
	    LOGGER.setUseParentHandlers(false);
	    LogFormatter format = new LogFormatter();
	    ConsoleHandler console = new ConsoleHandler();
	    console.setFormatter(format);
	    LOGGER.addHandler(console);
    }

    public Collection<GPlayer> getConnectedPlayers() {
        return connectedPlayers.values();
    }

    public  GPlayer getPlayerByEntityID(long id) {
        if(connectedPlayers.containsKey(id))
            return connectedPlayers.get(id);
        else
        {
            Glydar.getServer().getLogger().warning("Unable to find player with entity ID "+id+"! Returning null!");
            return null;
        }
    }
    
    public void addPlayer(long entityID, GPlayer p) {
    	if(!connectedPlayers.containsKey(entityID)) {
    		connectedPlayers.put(entityID, p);
        }
    }
    
    public void removePlayer(long entityID) {
    	connectedPlayers.remove(entityID);
    }
    
	public Logger getLogger() {
		return LOGGER;
	}

	public boolean isRunning() {
		return running;
	}

    @Override
    public void run() {
        while (this.isRunning()) {
            try {
            /* TODO Server loop / tick code.
               Eventually; All periodic events will be processed here, such as AI logic, etc for entities.
             */
            	
            	if (serverUpdatePacket.sud != null){
            		getLogger().info("Server Update Sent!");
            		serverUpdatePacket.sendToAll();
            		serverUpdatePacket = new Packet4ServerUpdate();
            	} //else {
            		//getLogger().info("Update Not Sent!");
            	//}
            	
                Thread.sleep(1); //To check shutdown
            } catch (InterruptedException ex) { break; }
        }
        getLogger().info("Goodbye!");
    }

    public void shutdown() {
        this.running = false;
    }
    
    public void broadcastMessage(String message) {
    	new Packet10Chat(message, 0).sendToAll();
    }
    
    public void broadcast(String message, String permission) {
        broadcast(message, new Permission(permission, PermissionDefault.TRUE));
    }

    public void broadcast(String message, Permission permission) {
        for (GPlayer player : this.getConnectedPlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessageToPlayer(message);
            }
        }
    }
    

}
