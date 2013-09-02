package org.glydar.glydar;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.glydar.glydar.models.GModelCreator;
import org.glydar.glydar.models.GWorld;
import org.glydar.glydar.netty.CubeWorldServerInitializer;
import org.glydar.glydar.netty.data.GDataCreator;
import org.glydar.paraglydar.ParaGlydar;
import org.glydar.paraglydar.event.manager.EventManager;
import org.glydar.paraglydar.plugin.PluginLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class Glydar {

	private static GServer s; //TODO command line arg for debug
	private static Thread serverThread = new Thread(s);
	private static final PluginLoader loader = new PluginLoader();
	private static ChannelFuture chan;
	private static ServerBootstrap serverBootstrap;
	private static boolean serverDebug = false;

	public static boolean ignorePacketErrors = false;

	public static void main(String[] args) {
		long startMillis = System.currentTimeMillis();
		if (args.length > 0) {
			for (String s : args) {
				if (s.equalsIgnoreCase("-ignorepacketerrors"))
					ignorePacketErrors = true;
				else if (s.equalsIgnoreCase("-debug"))
					serverDebug = true;
			}
		}
		s = new GServer(serverDebug);
		//TODO: MAke this configurable in config
		s.defaultWorld = new GWorld("Default", 12345);

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
            } catch (FileNotFoundException e) {
                s.getLogger().log(Level.SEVERE, "Couldn't find admins file.");
            }
        }
        s.setAdmins(admins);

		ParaGlydar.setServer(s);
		ParaGlydar.setCreatorAPI(new GModelCreator(), new GDataCreator());

		final int port = 12345;

		serverBootstrap = new ServerBootstrap();
		serverBootstrap.childHandler(new CubeWorldServerInitializer())
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 32 * 1024)
				.option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 64 * 1024)
				.group(new NioEventLoopGroup())
				.channelFactory(new ChannelFactory<ServerChannel>() {
					@Override
					public ServerChannel newChannel() {
						return new NioServerSocketChannel();
					}
				})
				.bind(new InetSocketAddress(port));

		try {
			loader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ParaGlydar.setPluginLoader(loader);

		s.getLogger().info("Server ready on port " + port);
		s.getLogger().info("This server is running " + s.getName() + " version " + s.getVersion());

		serverThread.start();
		long finishMillis = System.currentTimeMillis();
		s.getLogger().info("Server started in " + (finishMillis - startMillis) + "ms");
		s.run();
		
	}

	public static GServer getServer() {
		return s;
	}

	public static EventManager getEventManager() {
		return s.getEventManager();
	}

	public static void shutdown() {
		getServer().shutdown();
		serverThread.interrupt();
		chan.channel().close();
		serverBootstrap.childGroup().shutdownGracefully();
		serverBootstrap.group().shutdownGracefully();
	}

}
