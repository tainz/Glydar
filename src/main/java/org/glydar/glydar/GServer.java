package org.glydar.glydar;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glydar.glydar.models.GModelCreator;
import org.glydar.glydar.models.GNPC;
import org.glydar.glydar.models.GWorld;
import org.glydar.glydar.protocol.data.GDataCreator;
import org.glydar.glydar.protocol.server.Packet2UpdateFinished;
import org.glydar.glydar.protocol.shared.Packet10Chat;
import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.ServerConfig;
import org.glydar.paraglydar.command.manager.CommandManager;
import org.glydar.paraglydar.data.DataCreator;
import org.glydar.paraglydar.event.manager.EventManager;
import org.glydar.paraglydar.logging.GlydarLogger;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.EveryoneTarget;
import org.glydar.paraglydar.models.ModelCreator;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.permissions.Permission;
import org.glydar.paraglydar.permissions.Permission.PermissionDefault;
import org.glydar.paraglydar.plugin.PluginLoader;
import org.glydar.glydar.util.Versioning;

import com.google.common.collect.ImmutableList;

public class GServer implements Runnable, Server {

	public static final String SERVER_NAME = "Glydar";
	public static final String VERSION = Versioning.getParaGlydarVersion();

	private final Path baseFolder;
	private final GlydarLogger logger;
	private final GServerConfig config;
	private final PluginLoader pluginLoader;
	private final EventManager eventManager;
	private final CommandManager commandManager;
	private final Thread commandReader;
	private final DataCreator dataCreator;
	private final ModelCreator modelCreator;

	private boolean running = true;
	private Map<Long, World> serverWorlds;
	private Map<Long, Entity> connectedEntities;

	public GServer(GlydarBootstrap bootstrap) {
		this.baseFolder = bootstrap.getBaseFolder();
		this.logger = bootstrap.getLogger();
		this.config = new GServerConfig(this, bootstrap);
		this.pluginLoader = new PluginLoader();
		this.eventManager = new EventManager(logger);
		this.commandManager = new CommandManager(logger);
		this.commandReader = new ThreadedCommandReader(this);
		this.dataCreator = new GDataCreator();
		this.modelCreator = new GModelCreator();

		this.serverWorlds = new HashMap<>();
		this.connectedEntities = new HashMap<>();
	}

	void setUpWorlds() {
		for (ServerConfig.WorldConfig worldConfig : config.getAllWorldsConfigs()) {
			GWorld world = new GWorld(worldConfig.getName(), worldConfig.getSeed());
			world.setPVPAllowed(worldConfig.isPvpAllowed());
		}
	}

	@Override
	public String getName() {
		return SERVER_NAME;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public Path getBaseFolder() {
		return baseFolder;
	}

	@Override
	public Path getConfigFolder() {
		return baseFolder.resolve("config");
	}

	@Override
	public GlydarLogger getLogger() {
		return logger;
	}

	@Override
	public GServerConfig getConfig() {
		return config;
	}

	@Override
	public PluginLoader getPluginLoader() {
		return pluginLoader;
	}

	@Override
	public EventManager getEventManager() {
		return eventManager;
	}

	@Override
	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public DataCreator getDataCreator() {
		return dataCreator;
	}

	@Override
	public ModelCreator getModelCreator() {
		return modelCreator;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	public void shutdown() {
		this.commandReader.interrupt();
		this.running = false;
	}

	@Override
	public Collection<Player> getConnectedPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Entity e : connectedEntities.values()) {
			if (e instanceof Player) {
				players.add((Player) e);
			}
		}
		return players;
	}

	@Override
	public Collection<Entity> getConnectedEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (Entity e : connectedEntities.values()) {
			entities.add(e);
		}
		return entities;
	}

	@Override
	public Entity getEntityByEntityID(long id) {
		if (connectedEntities.containsKey(id))
			return connectedEntities.get(id);
		else {
			Glydar.getServer().getLogger().warning("Unable to find entity with entity ID " + id + "! Returning null!");
			return null;
		}
	}

	@Override
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

	public World getDefaultWorld() {
		return serverWorlds.get(1L);
	}

	public List<World> getWorlds() {
		return ImmutableList.copyOf(serverWorlds.values());
	}

	public boolean addWorld(World world) {
		if (serverWorlds.containsKey(world.getWorldId())) {
			return false;
		}

		serverWorlds.put(world.getWorldId(), world);
		return true;
	}

	@Override
	public void debug(String message) {
		if (config.isDebug()) {
			getLogger().info("[DEBUG] " + message);
		}
	}

	@Override
	public void run() {
		//TODO: Work on Default Entity which will be used for "faking" hit packets/xp packets and the lot!
		GNPC dummyNpc = new GNPC();
		dummyNpc.getEntityData().setName("dummy");
		dummyNpc.getEntityData().setHostileType((byte) 2);

		while (this.isRunning()) {
			try {
				//TODO: Figure out optimal system for FPS.
				if (System.currentTimeMillis() % (1000 / config.getFPS()) == 0) {
					//EntityUpdate events are controller through this loop. 
					for (Entity e : getConnectedEntities()) {
						e.forceUpdateData(true);
					}
					for (World w : getWorlds()) {
						new Packet2UpdateFinished().sendToWorld(w);
					}

					//WorldUpdatePackets are controlled here:
					for (World w : getWorlds()) {
						GWorld world = (GWorld) w;
						world.sendUpdate();
					}
				}

				Thread.sleep(1); //To check shutdown
			} catch (InterruptedException ex) {
				break;
			}
		}
		getLogger().info("Goodbye!");
	}
}
