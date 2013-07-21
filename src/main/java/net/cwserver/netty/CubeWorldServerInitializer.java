package net.cwserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import net.cwserver.models.Player;

public class CubeWorldServerInitializer extends ChannelInitializer<SocketChannel> {
	public static final AttributeKey<Player> PLAYER_ATTRIBUTE_KEY = new AttributeKey<Player>("player");

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();

		pipeline.addLast("framer", new CubeWorldFrameDecoder());
        pipeline.addLast("encoder", new CubeWorldByteEncoder());
		pipeline.addLast("handler", new CubeWorldPacketHandler());

	}
}
