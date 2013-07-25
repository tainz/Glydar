package org.glydar.glydar;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.glydar.glydar.netty.CubeWorldServerInitializer;

import java.net.InetSocketAddress;

public class Main {

    private static Server s = new Server(false); //TODO command line arg for debug

	public static void main(String[] args) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.childHandler(new CubeWorldServerInitializer());
		//serverBootstrap.option("child.tcpNoDelay", true);
		//serverBootstrap.setOption("child.keepAlive", true);
		serverBootstrap.group(new NioEventLoopGroup());
		serverBootstrap.channelFactory(new ChannelFactory<ServerChannel>() {
			@Override
			public ServerChannel newChannel() {
				return new NioServerSocketChannel();
			}
		});

		final int port = 12345;

		serverBootstrap.bind(new InetSocketAddress(port));

		System.out.println("Server ready on port " + port);
        new Thread(s).start();
	}

    public static Server getServer() {
        return s;
    }
}
