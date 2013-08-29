package org.glydar.glydar;

import org.glydar.paraglydar.Server;
import org.glydar.paraglydar.command.CommandManager;
import org.glydar.paraglydar.command.ConsoleCommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
				String[] cmd = bufferedReader.readLine().split(" ");
				String[] args = (String[]) Arrays.copyOfRange(cmd, 1, cmd.length);
				this.console.sendMessage("Executing command " + cmd[0]);
				CommandManager.exec(console, cmd[0], args);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			// Catch NPE
		}
	}

}
