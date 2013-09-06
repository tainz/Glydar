package org.glydar.glydar;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import org.glydar.glydar.models.GModelCreator;
import org.glydar.glydar.protocol.data.GDataCreator;
import org.glydar.glydar.protocol.pipeline.ProtocolInitializer;
import org.glydar.paraglydar.ParaGlydar;
import org.glydar.paraglydar.event.manager.EventManager;
import org.glydar.paraglydar.plugin.PluginLoader;

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

        GlydarConfig config = new GlydarConfig();
        config.setupServer(s);
		
		ParaGlydar.setServer(s);
		ParaGlydar.setCreatorAPI(new GModelCreator(), new GDataCreator());

		serverBootstrap = new ServerBootstrap();
		serverBootstrap.childHandler(new ProtocolInitializer())
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
				.bind(new InetSocketAddress(s.getPort()));

		try {
			loader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ParaGlydar.setPluginLoader(loader);

		s.getLogger().info("Server ready on port " + s.getPort());
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
