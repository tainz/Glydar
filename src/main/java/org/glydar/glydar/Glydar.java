package org.glydar.glydar;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.glydar.glydar.protocol.pipeline.ProtocolInitializer;
import org.glydar.paraglydar.ParaGlydar;
import org.glydar.paraglydar.event.manager.EventManager;

import com.google.common.base.Stopwatch;

public class Glydar {

	private static GServer server;
	private static ServerBootstrap serverBootstrap;
	private static Thread serverThread = new Thread(server);

	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		watch.start();

		GlydarBootstrap bootstrap = new GlydarBootstrap(args);
		server = new GServer(bootstrap);
		ParaGlydar.setServer(server);

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
				.bind(new InetSocketAddress(server.getConfig().getPort()));

		server.setUpWorlds();

		try {
			server.getPluginLoader().loadPlugins();
		} catch (Exception exc) {
			server.getLogger().warning(exc, "Error while loading plugins");
		}

		server.getLogger().info("Server ready on port {0}", server.getConfig().getPort());
		server.getLogger().info("This server is running {0} version {1}", server.getName(), server.getVersion());

		watch.stop();
		server.getLogger().info("Server started in {0}ms", watch.elapsed(TimeUnit.MILLISECONDS));
		serverThread.start();
	}

	public static GServer getServer() {
		return server;
	}

	public static EventManager getEventManager() {
		return server.getEventManager();
	}

	public static void shutdown() {
		getServer().shutdown();
		serverThread.interrupt();
		serverBootstrap.childGroup().shutdownGracefully();
		serverBootstrap.group().shutdownGracefully();
	}
}
