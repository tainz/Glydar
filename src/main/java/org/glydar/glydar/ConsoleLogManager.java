package org.glydar.glydar;

import org.glydar.glydar.util.LoggerOutputStream;

import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogManager implements IConsoleLogManager {

	private final Logger logger;

	public ConsoleLogManager(String name) {
		this.logger = Logger.getLogger(name);
		this.init();
	}

	private void init() {
		this.logger.setUseParentHandlers(false);

		ConsoleLogFormatter formatter = new ConsoleLogFormatter();
		ConsoleHandler console = new ConsoleHandler();

		console.setFormatter(formatter);
		this.logger.addHandler(console);

		System.setOut(new PrintStream(new LoggerOutputStream(Level.INFO, getLogger()), true));
		System.setErr(new PrintStream(new LoggerOutputStream(Level.SEVERE, getLogger()), true));
	}

	public Logger getLogger() {
		return this.logger;
	}

	public void info(String message) {
		this.logger.log(Level.INFO, message);
	}

	public void warning(String message) {
		this.logger.log(Level.WARNING, message);
	}

	public void warning(String message, Object... object) {
		this.logger.log(Level.WARNING, message, object);
	}

	public void warning(String message, Throwable throwable) {
		this.logger.log(Level.WARNING, message, throwable);
	}

	public void severe(String message) {
		this.logger.log(Level.SEVERE, message);
	}

	public void severe(String message, Throwable throwable) {
		this.logger.log(Level.SEVERE, message, throwable);
	}
}
