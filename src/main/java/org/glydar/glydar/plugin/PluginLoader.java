package org.glydar.glydar.plugin;

import java.io.File;
import java.net.URLClassLoader;
import java.util.List;

public interface PluginLoader {

	public void loadPlugin(File file) throws PluginException;

	public List<Plugin> getPlugins();

	public void enablePlugin(Plugin plugin);

	public void disablePlugin(Plugin plugin);

	public URLClassLoader getClassLoader(Plugin plugin);
}
