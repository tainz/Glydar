package org.glydar.glydar;

import org.glydar.glydar.netty.packet.server.Packet4ServerUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.command.manager.CommandManager;
import org.glydar.paraglydar.event.manager.EventManager;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.EveryoneTarget;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.permissions.Permission;
import org.glydar.paraglydar.permissions.Permission.PermissionDefault;
import org.glydar.glydar.util.Versioning;

import java.util.*;
import java.util.logging.Logger;

public class GServer implements Runnable, Server {

	private final IConsoleLogManager logManager;
	private boolean running = true;
	public final boolean DEBUG;
	public Packet4ServerUpdate serverUpdatePacket = new Packet4ServerUpdate();
	
	private final EventManager eventManager;
	private final CommandManager commandManager;
	
	private HashMap<Long, Entity> connectedEntities = new HashMap<Long, Entity>();
	private HashMap<Long, World> serverWorlds = new HashMap<Long, World>();
	protected World defaultWorld;
	
	private final String serverName = "Glydar";
	private final String serverVersion = Versioning.getParaGlydarVersion();
	private Thread commandReader;
    private List<String> admins = new ArrayList<>();
    private int maxPlayers;
    private boolean allowPVP;
	private int port;

    public GServer(boolean debug) {
		this.DEBUG = debug;
		this.logManager = new ConsoleLogManager(Glydar.class.getName());
		this.eventManager = new EventManager(logManager.getLogger());
		this.commandManager = new CommandManager(logManager.getLogger());

		this.init();
	}

	public void init() {
		commandReader = new ThreadedCommandReader(this);

		commandReader.setDaemon(true);
		commandReader.start();
	}

	public EventManager getEventManager() {
		return eventManager;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}

	public String getName() {
		return serverName;
	}

	public String getVersion() {
		return serverVersion;
	}

	public Collection<Player> getConnectedPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Entity e : connectedEntities.values()) {
			if (e instanceof Player) {
				players.add((Player) e);
			}
		}
		return players;
	}

	public Collection<Entity> getConnectedEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (Entity e : connectedEntities.values()) {
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
			if (connectedEntities.get(id) instanceof Player) {
				return (Player) connectedEntities.get(id);
			}
		}
		Glydar.getServer().getLogger().warning("Unable to find player with entity ID " + id + "! Returning null!");
		return null;
	}

	public void addEntity(long entityID, Entity e) {
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

	public void shutdown() {
		this.commandReader.interrupt();
		this.running = false;
	}

	public void broadcastMessage(String message) {
		new Packet10Chat(message, 0).sendTo(EveryoneTarget.INSTANCE);
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
	
	public void addWorld(World w){
		if (!serverWorlds.containsKey(w.getWorldId())){
			serverWorlds.put(w.getWorldId(), w);
		}
	}
	
	public World getDefaultWorld(){
		return defaultWorld;
	}
	
	public Map<Long, World> getWorlds(){
		HashMap<Long,World> m = new HashMap<Long, World>();
		m.putAll(serverWorlds);
		return m;
	}
    
    public List<String> getAdmins() {
        return admins;
    }

    protected void setAdmins(List<String> admins) {
        this.admins = admins;
    }

	@Override
	public void run() {
		while (this.isRunning()) {
			try {
	        /* TODO Server loop / tick code.
               Eventually; All periodic events will be processed here, such as AI logic, etc for entities.
             */

				if (serverUpdatePacket.sud != null && System.currentTimeMillis() % 250 == 0) {
					getLogger().info("Server Update Sent!");
					serverUpdatePacket.sendToWorld(defaultWorld);
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

	@Override
	public boolean isDebugging() {
		return this.DEBUG;
	}

	@Override
	public void debug(String message) {
		if (isDebugging()) {
			getLogger().info("[DEBUG] " + message);
		}
	}
	
	public boolean isPVPAllowed() {
		return allowPVP;
	}
	
	public void setPVPAllowed(boolean allowPVP) {
		this.allowPVP = allowPVP;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public int getPort() {
		return port;
	}

	protected void setPort(int port) {
		this.port = port;
	}
}
