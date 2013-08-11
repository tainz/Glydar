package org.glydar.glydar;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.glydar.api.plugin.PluginLoader;
import org.glydar.glydar.netty.CubeWorldServerInitializer;

import java.net.InetSocketAddress;
import java.nio.channels.Channel;

public class Glydar {

    private static GServer s; //TODO command line arg for debug
    private static Thread serverThread = new Thread(s);
	private static final PluginLoader loader = new PluginLoader();
    private static ChannelFuture chan;
    private static ServerBootstrap serverBootstrap;
    private static boolean serverDebug = false;

    public static boolean ignorePacketErrors = false;

    public static void main(String[] args) {
        if(args.length > 0) {
            for(String s : args)
            {
               if(s.equalsIgnoreCase("-ignorepacketerrors"))
                   ignorePacketErrors = true;
                else if(s.equalsIgnoreCase("-debug"))
                   serverDebug = true;
            }
        }
        s = new GServer(serverDebug);
		serverBootstrap = new ServerBootstrap();
		serverBootstrap.childHandler(new CubeWorldServerInitializer());
		serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
		serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		//serverBootstrap.setOption("child.keepAlive", true);
		serverBootstrap.group(new NioEventLoopGroup());
		serverBootstrap.channelFactory(new ChannelFactory<ServerChannel>() {
			@Override
			public ServerChannel newChannel() {
				return new NioServerSocketChannel();
			}
		});

		try {
			loader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}

		final int port = 12345;

		chan = serverBootstrap.bind(new InetSocketAddress(port));

		s.getLogger().info("Server ready on port " + port);
        serverThread.start();
	}

    public static GServer getServer() {
        return s;
    }

    public static void shutdown() {
        getServer().shutdown();
        serverThread.interrupt();
        chan.channel().close();
        serverBootstrap.childGroup().shutdownGracefully();
        serverBootstrap.group().shutdownGracefully();
    }

}
