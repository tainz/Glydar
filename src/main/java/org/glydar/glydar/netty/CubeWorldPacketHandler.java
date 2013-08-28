package org.glydar.glydar.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

import java.io.IOException;

public class CubeWorldPacketHandler extends SimpleChannelInboundHandler<CubeWorldPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, CubeWorldPacket cubeWorldPacket) throws Exception {
		Attribute<GPlayer> playerAttrib = channelHandlerContext.attr(CubeWorldServerInitializer.PLAYER_ATTRIBUTE_KEY);
		GPlayer player = playerAttrib.get();
		if (player == null) {
			player = new GPlayer();
			player.setChannelContext(channelHandlerContext);
			playerAttrib.set(player);
		}
		try {
			cubeWorldPacket.receivedFrom(player);
		} catch (IllegalAccessError e) {
			CubeWorldPacket.Packet packet = (CubeWorldPacket.Packet) cubeWorldPacket.getClass().getAnnotation(CubeWorldPacket.Packet.class);
			Glydar.getServer().getLogger().warning("No handler for packet ID " + packet.id());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			GPlayer player = ctx.attr(CubeWorldServerInitializer.PLAYER_ATTRIBUTE_KEY).get();

			if (player != null) {
				Glydar.getServer().getLogger().info("Player " + player.getName() + " (ID: "
						+ player.getEntityId() + ")" + " has disconnected!");
				player.playerLeft();
				ctx.close();
			}
		}
	}
}
