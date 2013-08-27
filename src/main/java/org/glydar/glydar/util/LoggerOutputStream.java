package org.glydar.glydar.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerOutputStream extends ByteArrayOutputStream {

	private static final String lineSeparator = System.getProperty("line.separator");

	private final Level level;
	private final Logger logger;

	public LoggerOutputStream(Level level, Logger logger) {
		this.level = level;
		this.logger = logger;
	}

	@Override
	public void flush() throws IOException {
		synchronized (this) {
			super.flush();

			String message = toString().replace(lineSeparator, "");
			if (message.length() > 0) {
				LogRecord record = new LogRecord(level, message);
				logger.log(record);
			}

			reset();
		}
	}
}
