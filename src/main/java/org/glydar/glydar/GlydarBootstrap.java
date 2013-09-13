package org.glydar.glydar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glydar.paraglydar.i18n.I18nTarget;
import org.glydar.paraglydar.logging.GlydarLogger;
import org.glydar.paraglydar.logging.GlydarLoggerFormatter;

import com.google.common.collect.ImmutableList;

class GlydarBootstrap implements I18nTarget {

	private static final String BACKUP_SUFFIX = ".bk";
	private static final String LOGS_FOLDER_NAME = "logs";
	private static final String CONFIG_FOLDER_NAME = "config";
	private static final String PLUGINS_FOLDER_NAME = "plugins";
	private static final String CONFIG_FILE_NAME = "glydar.yml";

	private final Logger logger;
	private Boolean debugOverride;
	private Integer portOverride;
	private Integer fpsOverride;
	private Path baseFolder;
	private I18nTarget i18nTarget;
	private GlydarLogger serverLogger;
	private Path configFile;

	public GlydarBootstrap(String[] args) {
		this.logger = Logger.getLogger(getClass().getCanonicalName());

		parseArguments(args);
		setupFolders();
		setupLogger();
		readConfig();
	}

	@Override
	public Iterable<URL> getI18nLocations(String filename) {
		return Collections.<URL> emptyList();
	}

	private void parseArguments(String[] args) {
		Pattern debugPattern = Pattern.compile("-(no)?debug");
		Pattern portPattern = Pattern.compile("-port=([0-9]{1,5})");
		Pattern fpsPattern = Pattern.compile("-fps=([0-9]{1,3})");

		for (String arg : args) {
			Matcher debugMatcher = debugPattern.matcher(arg);
			if (debugMatcher.matches()) {
				debugOverride = arg.equals("-debug");
				continue;
			}

			Matcher portMatcher = portPattern.matcher(arg);
			if (portMatcher.matches()) {
				portOverride = Integer.parseInt(portMatcher.group(1));
				continue;
			}

			Matcher fpsMatcher = fpsPattern.matcher(arg);
			if (fpsMatcher.matches()) {
				fpsOverride = Integer.parseInt(fpsMatcher.group(1));
				continue;
			}
		}
	}

	private void setupFolders() {
		try {
			URI sourceUri = Glydar.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			Path path = Paths.get(sourceUri).getParent();
			baseFolder = path;
		} catch (URISyntaxException exc) {
			logger.log(Level.WARNING, "Error while trying to discover base folder using code source location", exc);
			baseFolder = Paths.get("");
		}

		tryCreateFolder(LOGS_FOLDER_NAME);
		tryCreateFolder(CONFIG_FOLDER_NAME);
		tryCreateFolder(PLUGINS_FOLDER_NAME);

		i18nTarget = new GlydarI18nTarget(baseFolder);
	}

	private Path tryCreateFolder(String name) {
		Path folder = baseFolder.resolve(name);

		if (!Files.isDirectory(folder)) {
			try {
				if (Files.exists(folder)) {
					Path backupPath = folder.resolveSibling(name + BACKUP_SUFFIX);
					Files.move(folder, backupPath);
				}
				Files.createDirectory(folder);
			} catch (IOException exc) {
				logger.log(Level.WARNING, "Error while trying to create missing folder " + name, exc);
			}
		}

		return folder;
	}

	public void setupLogger() {
		serverLogger = GlydarLogger.of(GServer.SERVER_NAME, i18nTarget);
		serverLogger.getJdkLogger().setUseParentHandlers(false);

		GlydarLoggerFormatter formatter = new GlydarLoggerFormatter(false);

		ConsoleHandler consoleHandler = new ConsoleHandler();
		try {
			consoleHandler.setEncoding(StandardCharsets.UTF_8.name());
		} catch (SecurityException | UnsupportedEncodingException exc) {
			logger.log(Level.WARNING, "Unable to set console logger handler to utf8 encoding", exc);
		}
		consoleHandler.setFormatter(formatter);
		serverLogger.getJdkLogger().addHandler(consoleHandler);

		try {
			Path logsFile = baseFolder.resolve("logs").resolve("glydar.log");
			FileHandler fileHandler = new FileHandler(logsFile.toString(), true);
			try {
				fileHandler.setEncoding(StandardCharsets.UTF_8.name());
			} catch (SecurityException | UnsupportedEncodingException exc) {
				logger.log(Level.WARNING, "Unable to set file logger handler to utf8 encoding", exc);
			}
			fileHandler.setFormatter(formatter);
			serverLogger.getJdkLogger().addHandler(fileHandler);
		} catch (SecurityException | IOException exc) {
			logger.log(Level.WARNING, "Unable to open log file", exc);
		}
	}

	public void readConfig() {
		this.configFile = baseFolder.resolve(CONFIG_FOLDER_NAME).resolve(CONFIG_FILE_NAME);
		if (!Files.isRegularFile(configFile)) {
			try {
				if (Files.exists(configFile)) {
					Path backupPath = configFile.resolveSibling(CONFIG_FILE_NAME + BACKUP_SUFFIX);
					Files.move(configFile, backupPath);
				}
				Files.createFile(configFile);
			} catch (Exception exc) {
				logger.log(Level.WARNING, "Error while trying to create missing file " + CONFIG_FILE_NAME);
			}
		}
	}

	public Path getBaseFolder() {
		return baseFolder;
	}

	public GlydarLogger getLogger() {
		return serverLogger;
	}

	public Path getConfigFile() {
		return configFile;
	}

	public Boolean getDebugOverride() {
		return debugOverride;
	}

	public Integer getPortOverride() {
		return portOverride;
	}

	public Integer getFpsOverride() {
		return fpsOverride;
	}
}

class GlydarI18nTarget implements I18nTarget {

	private final Path baseFolder;

	public GlydarI18nTarget(Path baseFolder) {
		this.baseFolder = baseFolder;
	}

	@Override
	public Iterable<URL> getI18nLocations(String name) {
		ImmutableList.Builder<URL> builder = ImmutableList.builder();

		URL bundledLocation = getClass().getResource(name);
		if (bundledLocation != null) {
			builder.add(bundledLocation);
		}

		Path path = baseFolder.resolve(name);
		try {
			builder.add(path.toUri().toURL());
		} catch (MalformedURLException exc) {
			// Not handled
		}

		return builder.build();
	}
}