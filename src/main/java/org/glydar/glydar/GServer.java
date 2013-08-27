package org.glydar.glydar;

import org.glydar.glydar.models.GEntity;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.server.Packet4ServerUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.event.manager.EventManager;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.permissions.Permission;
import org.glydar.paraglydar.permissions.Permission.PermissionDefault;
import org.glydar.glydar.util.LoggerOutputStream;
import org.glydar.glydar.util.Versioning;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GServer implements Runnable, Server {

	private final IConsoleLogManager logManager;
	private boolean running = true;
	public final boolean DEBUG;
	public Packet4ServerUpdate serverUpdatePacket = new Packet4ServerUpdate();
	private final EventManager eventManager;
	private HashMap<Long, GEntity> connectedEntities = new HashMap<Long, GEntity>();
	private final String serverName = "Glydar";
	private final String serverVersion = Versioning.getParaGlydarVersion();

	public GServer(boolean debug) {
		this.DEBUG = debug;
		this.logManager = new ConsoleLogManager(Glydar.class.getName());
		System.setOut(new PrintStream(new LoggerOutputStream(Level.INFO, logManager.getLogger()), true));
		System.setErr(new PrintStream(new LoggerOutputStream(Level.SEVERE, logManager.getLogger()), true));
		this.eventManager = new EventManager();
	}

	@Override
	public EventManager getEventManager() {
		return eventManager;
	}

	public String getName() {
		return serverName;
	}

	public String getVersion() {
		return serverVersion;
	}

	public Collection<Player> getConnectedPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (GEntity e : connectedEntities.values()) {
			if (e instanceof GPlayer) {
				players.add((Player) e);
			}
		}
		return players;
	}

	public Collection<Entity> getConnectedEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (GEntity e : connectedEntities.values()) {
			entities.add(e);
		}
		return entities;
	}

	public Entity getEntityByEntityID(long id) {
		if (connectedEntities.containsKey(id))
			return connectedEntities.get(id);
		else {
			Glydar.getServer().getLogger().warning("Unable to find entity with entity ID " + id + "! Returning null!");
			return null;
		}
	}

	public Player getPlayerByEntityID(long id) {
		if (connectedEntities.containsKey(id)) {
			if (connectedEntities.get(id) instanceof GPlayer) {
				return (GPlayer) connectedEntities.get(id);
			}
		}
		Glydar.getServer().getLogger().warning("Unable to find player with entity ID " + id + "! Returning null!");
		return null;
	}

	public void addEntity(long entityID, GEntity e) {
		if (!connectedEntities.containsKey(entityID)) {
			connectedEntities.put(entityID, e);
		}
	}

	public void removeEntity(long entityID) {
		connectedEntities.remove(entityID);
	}

	public Logger getLogger() {
		return this.logManager.getLogger();
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

				if (serverUpdatePacket.sud != null) {
					getLogger().info("Server Update Sent!");
					serverUpdatePacket.sendToAll();
					serverUpdatePacket = new Packet4ServerUpdate();
				} //else {
				//getLogger().info("Update Not Sent!");
				//}

				Thread.sleep(1); //To check shutdown
			} catch (InterruptedException ex) {
				break;
			}
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
