package org.glydar.glydar.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

import java.io.IOException;

public class CubeWorldPacketHandler extends SimpleChannelInboundHandler<CubeWorldPacket> {
	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, CubeWorldPacket cubeWorldPacket) throws Exception {
		Attribute<Player> playerAttrib = channelHandlerContext.attr(CubeWorldServerInitializer.PLAYER_ATTRIBUTE_KEY);
		Player player = playerAttrib.get();
		if(player == null) {
			player = new Player();
            player.setChannelContext(channelHandlerContext);
			playerAttrib.set(player);
		}
        try {
		    cubeWorldPacket.receivedFrom(player);
        } catch (IllegalAccessError e) {
            //System.out.println("No handler for packet ID "+((CubeWorldPacket.Packet)cubeWorldPacket.getClass().getAnnotation(CubeWorldPacket.Packet.class)).id());
        }
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			System.out.println("Player has disconnected");

			for (Player p : Player.getConnectedPlayers()) {
				if (ctx.channel() == p.getChannelContext().channel()) {
					// TODO Dispose of player data.
				}
			}
		}
	}
}
