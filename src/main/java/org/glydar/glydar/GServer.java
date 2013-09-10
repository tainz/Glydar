package org.glydar.glydar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

import org.glydar.glydar.models.GNPC;
import org.glydar.glydar.models.GWorld;
import org.glydar.glydar.protocol.server.Packet2UpdateFinished;
import org.glydar.glydar.protocol.shared.Packet10Chat;
import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.command.manager.CommandManager;
import org.glydar.paraglydar.event.manager.EventManager;
import org.glydar.paraglydar.i18n.I18nTarget;
import org.glydar.paraglydar.logging.GlydarLogger;
import org.glydar.paraglydar.logging.GlydarLoggerFormatter;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.EveryoneTarget;
import org.glydar.paraglydar.models.NPC;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.permissions.Permission;
import org.glydar.paraglydar.permissions.Permission.PermissionDefault;
import org.glydar.glydar.util.Versioning;

import com.google.common.collect.ImmutableList;

public class GServer implements Runnable, Server, I18nTarget {

	private final Path baseFolder;
	private final GlydarLogger logger;
	private boolean running = true;
	public final boolean DEBUG;
	
	private final EventManager eventManager;
	private final CommandManager commandManager;
	
	private HashMap<Long, Entity> connectedEntities = new HashMap<Long, Entity>();
	private HashMap<Long, World> serverWorlds = new HashMap<Long, World>();
	public NPC n;
	
	private final String serverName = "Glydar";
	private final String serverVersion = Versioning.getParaGlydarVersion();
	private final Thread commandReader;
    private List<String> admins = new ArrayList<>();
    
    private int maxPlayers;
	private int port;
	private int fps;

    public GServer(boolean debug) {
		this.DEBUG = debug;

		this.baseFolder = initBaseFolder();
		this.logger = initLogger();
		this.eventManager = new EventManager(logger);
		this.commandManager = new CommandManager(logger);
		this.commandReader = initConsoleCommandReader();

		initFolders();
	}

	private Path initBaseFolder() {
		try {
			URI sourceUri = Glydar.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			Path path = Paths.get(sourceUri).getParent();
			if (Files.isDirectory(path)) {
				return path;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return Paths.get("");
	}

	private GlydarLogger initLogger() {
		GlydarLogger logger = GlydarLogger.of(serverName, this);
		logger.getJdkLogger().setUseParentHandlers(false);

		GlydarLoggerFormatter formatter = new GlydarLoggerFormatter(false);

		ConsoleHandler consoleHandler = new ConsoleHandler();
		try {
			consoleHandler.setEncoding(StandardCharsets.UTF_8.name());
		} catch (SecurityException | UnsupportedEncodingException exc) {
			// Should not happened
		}
		consoleHandler.setFormatter(formatter);
		logger.getJdkLogger().addHandler(consoleHandler);

		try {
			Path logsFolder = baseFolder.resolve("logs");
			Files.createDirectories(logsFolder);
			FileHandler fileHandler = new FileHandler(logsFolder.resolve("glydar.log").toString(), true);
			try {
				fileHandler.setEncoding(StandardCharsets.UTF_8.name());
			} catch (SecurityException | UnsupportedEncodingException exc) {
				// Should not happened
			}
			fileHandler.setFormatter(formatter);
			logger.getJdkLogger().addHandler(fileHandler);
		} catch (SecurityException | IOException exc) {
			logger.warning(exc, "Unable to open log file");
		}

		return logger;
	}

	private ThreadedCommandReader initConsoleCommandReader() {
		ThreadedCommandReader commandReader = new ThreadedCommandReader(this);
		commandReader.setDaemon(true);
		commandReader.start();
		return commandReader;
	}

	private void initFolders() {
		try {
			Files.createDirectories(getConfigFolder());
		} catch (IOException exc) {
			logger.severe(exc, "Unable to create config directory");
		}
	}

	@Override
	public Path getBaseFolder() {
		return baseFolder;
	}

	@Override
	public Path getConfigFolder() {
		return baseFolder.resolve("config");
	}

	public Iterable<URL> getI18nLocations(String name) {
		ImmutableList.Builder<URL> builder = ImmutableList.builder();

		// TODO: Figure out how to setup I18nLoader before logger 
		/*URL bundledLocation = getClass().getResource(name);
		if (bundledLocation != null) {
			builder.add(bundledLocation);
		}

		Path path = getBaseFolder().resolve(name);
		try {
			builder.add(path.toUri().toURL());
		} catch (MalformedURLException exc) {
			// Not handled
		}*/

		return builder.build();
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

	public GlydarLogger getLogger() {
		return logger;
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
		for (World world : getWorlds()) {
			if (world.getWorldId() == w.getWorldId()) {
				return;
			}
		}
		serverWorlds.put(w.getWorldId(), w);
	}
	
	public World getDefaultWorld(){
		return serverWorlds.get(1L);
	}
	
	public List<World> getWorlds(){
		List<World> worlds = new ArrayList<>();
		worlds.addAll(serverWorlds.values());
		return worlds;
	}
    
    public List<String> getAdmins() {
        return admins;
    }

    protected void setAdmins(List<String> admins) {
        this.admins = admins;
    }

	@Override
	public void run() {
		//TODO: Work on this
		//Default Entity which will be used for "faking" hit packets/xp packets and the lot!
		n = new GNPC();
		n.getEntityData().setName("dummy");
		n.getEntityData().setHostileType((byte) 2);
		
		while (this.isRunning()) {
			try {
	        /* TODO Server loop / tick code.
               Eventually; All periodic events will be processed here, such as AI logic, etc for entities.
             */
				
				
				//TODO: Figure out optimal system for FPS.
				if (System.currentTimeMillis() % (1000/fps) == 0){
					//EntityUpdate events are controller through this loop. 
					for (Entity e : getConnectedEntities()){
						e.forceUpdateData(true);
					}
					for (World w : getWorlds()){
						new Packet2UpdateFinished().sendToWorld(w);
					}

					//WorldUpdatePackets are controlled here:
					for (World w : getWorlds()){
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
	
	protected void setFps(int fps) {
		this.fps = fps;
	}
}
