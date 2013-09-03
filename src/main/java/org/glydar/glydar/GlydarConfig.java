package org.glydar.glydar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import org.glydar.glydar.models.GWorld;
import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.configuration.file.YamlConfiguration;

public class GlydarConfig {
	
	private final File file;
	private YamlConfiguration config;

	protected GlydarConfig() {
		file = new File("glydar.yml");
		if (!file.isFile()) {
			try {
				file.createNewFile();
			} catch (Exception e) {}
		}
		config = YamlConfiguration.loadConfiguration(file);
		config.addDefault("max-players", 4);
		config.addDefault("port", 12345);
		config.addDefault("worlds.default-world.name", "Default");
		config.addDefault("worlds.default-world.seed", 1111);
		config.options().copyDefaults(true);
		try {
			config.save(file);
		} catch (IOException e) {}
	}
	
	protected YamlConfiguration getConfig() {
		return config;
	}
	
	protected void setupServer(Server server) {
		server.setMaxPlayers(config.getInt("max-players"));
		((GServer)server).setPort(config.getInt("port"));
		setUpWorlds();
		setUpAdmins(server);
	}
	
	private void setUpWorlds(){
		new GWorld(config.getString("worlds.default-world.name"),config.getInt("default-world.seed"));
		
		for (String key : config.getConfigurationSection("worlds").getKeys(false)) {
			String name = config.getString("worlds." + key + ".name");
			int seed = config.getInt("worlds." + key + ".seed");
			new GWorld(name, seed);
		}
	}
	
	private void setUpAdmins(Server s){
		File adminsFile = new File("admins.txt");
        List<String> admins = new ArrayList<>();
        if (!adminsFile.exists()) {
            try {
                adminsFile.createNewFile();
            } catch (Exception e) {
                s.getLogger().log(Level.SEVERE, "Could not create admins file.");
            }
        } else {
            try {
                Scanner scanner = new Scanner(adminsFile);
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    if (line == null || line.equals("")){
                    } else {
                        admins.add(line.trim());
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                s.getLogger().log(Level.SEVERE, "Couldn't find admins file.");
            }
        }
        ((GServer) s).setAdmins(admins);
	}
	
	protected int getPort() {
		return config.getInt("port");
	}

}
