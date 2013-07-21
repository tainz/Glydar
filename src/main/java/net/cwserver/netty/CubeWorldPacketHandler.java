package net.cwserver.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.CubeWorldPacket;

public class CubeWorldPacketHandler extends SimpleChannelInboundHandler<CubeWorldPacket> {
	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, CubeWorldPacket cubeWorldPacket) throws Exception {
		Attribute<Player> playerAttrib = channelHandlerContext.attr(CubeWorldServerInitializer.PLAYER_ATTRIBUTE_KEY);
		Player player = playerAttrib.get();
		if(player == null) {
			player = new Player();
			playerAttrib.set(player);
		}
		cubeWorldPacket.receivedFrom(player);
	}
}
