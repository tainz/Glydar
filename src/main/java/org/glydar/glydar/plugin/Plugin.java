package org.glydar.glydar.plugin;

import org.glydar.glydar.Server;

import java.io.*;
import java.net.URLClassLoader;

public abstract class Plugin {

	private PluginLoader loader;
	private PluginLogger logger;
	private Server server;
	private boolean enabled = false;

	public void onEnable() {}

	public void onDisable() {}

	public abstract String getVersion();

	public abstract String getName();

	public String getAuthor() {
		return null;
	}

	public PluginLogger getLogger() {
		return logger;
	}

	public PluginLoader getLoader() {
		return loader;
	}

	public Server getServer() {
		return server;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean b) {
		if (enabled && b)
			return;
		else if (!enabled && !b)
			return;
		else if (b) {
			enabled = b;
			onEnable();
		} else {
			enabled = b;
			onDisable();
		}
	}

	public File getFolder() {
		File file = new File("plugins/" + getName());
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public InputStream getResource(String name) {
		URLClassLoader cl = loader.getClassLoader(this);
		return cl.getResourceAsStream(name);
	}

	public void saveResource(String name) {
		File file = new File(getFolder(), name);
		saveResource(name, file);
	}

	public void saveResource(String name, File file) {
		InputStream in = null;
		OutputStream out = null;
		try {
			file.createNewFile();
			in = getResource(name);
			out = new FileOutputStream(file);
			if (in == null)
				throw new PluginException("Could not find resource " + file.getName());
			byte[] buffer = new byte[1024];
			int len = in.read(buffer);
			while (len != -1) {
				out.write(buffer, 0, len);
				len = in.read(buffer);
			}
		} catch (Exception e) {
			logger.warning("Error while saving file " + file.getName() + ": " + e.getMessage());
			e.printStackTrace();
			return;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

	protected final void initialize(Server server, PluginLoader loader, PluginLogger logger) {
		this.server = server;
		this.logger = logger;
		this.loader = loader;
	}

}
