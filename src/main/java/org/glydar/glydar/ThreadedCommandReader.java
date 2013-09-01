package org.glydar.glydar;

import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.command.ConsoleCommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadedCommandReader extends Thread {

	final private Server server;
	final private ConsoleCommandSender console;

	ThreadedCommandReader(Server server) {
		this.server = server;
		this.console = new ConsoleCommandSender();
	}

	public void run() {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		try {
			while (this.server.isRunning()) {
				String cmdLine = bufferedReader.readLine();
				this.console.sendMessage("Executing command " + cmdLine);
				server.getCommandManager().execute(console, cmdLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			// Catch NPE
		}
	}

}
