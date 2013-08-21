package org.glydar.glydar;

import org.glydar.api.Server;
import org.glydar.api.models.Entity;
import org.glydar.api.models.Player;
import org.glydar.api.permissions.Permission;
import org.glydar.api.permissions.Permission.PermissionDefault;
import org.glydar.glydar.models.GEntity;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.server.Packet4ServerUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.glydar.util.LogFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class GServer implements Runnable, Server {

	private final Logger LOGGER = Logger.getLogger(Glydar.class.getName());

    private boolean running = true;

    public final boolean DEBUG;
    
    public Packet4ServerUpdate serverUpdatePacket = new Packet4ServerUpdate();
    
    private HashMap<Long, GEntity> connectedEntities = new HashMap<Long, GEntity>();

    public GServer(boolean debug) {
        this.DEBUG = debug;
	    LOGGER.setUseParentHandlers(false);
	    LogFormatter format = new LogFormatter();
	    ConsoleHandler console = new ConsoleHandler();
	    console.setFormatter(format);
	    LOGGER.addHandler(console);
    }

    public Collection<Player> getConnectedPlayers() {
    	ArrayList<Player> players = new ArrayList<Player>();
    	for (GEntity e : connectedEntities.values()){
    		if (e instanceof GPlayer){
    			players.add((Player) e);
    		}
    	}
        return players;
    }
    
    public Collection<Entity> getConnectedEntities() {
    	ArrayList<Entity> entities = new ArrayList<Entity>();
    	for (GEntity e : connectedEntities.values()){
    		entities.add(e);
    	}
        return entities;
    }

    public Entity getEntityByEntityID(long id) {
        if(connectedEntities.containsKey(id))
            return connectedEntities.get(id);
        else
        {
            Glydar.getServer().getLogger().warning("Unable to find entity with entity ID "+id+"! Returning null!");
            return null;
        }
    }
    
    public Player getPlayerByEntityID(long id) {
        if(connectedEntities.containsKey(id)) {
        	if (connectedEntities.get(id) instanceof GPlayer){
        		return (GPlayer) connectedEntities.get(id);
        	}
    	}
        Glydar.getServer().getLogger().warning("Unable to find player with entity ID "+id+"! Returning null!");
        return null;
    }
    
    public void addEntity(long entityID, GEntity e) {
    	if(!connectedEntities.containsKey(entityID)) {
    		connectedEntities.put(entityID, e);
        }
    }
    
    public void removeEntity(long entityID) {
    	connectedEntities.remove(entityID);
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
        for (Player player : this.getConnectedPlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessageToPlayer(message);
            }
        }
    }
    

}
