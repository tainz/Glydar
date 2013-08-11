package org.glydar.glydar.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.glydar.glydar.models.GPlayer;

public class CubeWorldServerInitializer extends ChannelInitializer<SocketChannel> {
	public static final AttributeKey<GPlayer> PLAYER_ATTRIBUTE_KEY = new AttributeKey<GPlayer>("player");

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();

		pipeline.addLast("framer", new CubeWorldFrameDecoder());
        pipeline.addLast("encoder", new CubeWorldByteEncoder());
		pipeline.addLast("handler", new CubeWorldPacketHandler());

	}
}
