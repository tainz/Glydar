package org.glydar.glydar;

import java.io.File;

import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.configuration.file.YamlConfiguration;

public class GlydarConfig {
	
	private final File file;
	private YamlConfiguration config;

	protected GlydarConfig() {
		file = new File("glydar.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				config = YamlConfiguration.loadConfiguration(file);
				config.set("pvp", "false");
				config.set("max-players", 4);
				config.set("port", 12345);
				config.save(file);
			} catch (Exception e) {}
		} else {
			config = YamlConfiguration.loadConfiguration(file);
		}
	}
	
	protected void setupServer(Server server) {
		server.setPVPAllowed(config.getBoolean("pvp"));
		server.setMaxPlayers(config.getInt("max-players"));
	}
	
	protected int getPort() {
		return config.getInt("port");
	}

}
