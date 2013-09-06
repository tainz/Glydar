package org.glydar.glydar.protocol.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

import java.io.IOException;

public class ProtocolHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet cubeWorldPacket) throws Exception {
		Attribute<GPlayer> playerAttrib = channelHandlerContext.attr(ProtocolInitializer.PLAYER_ATTRIBUTE_KEY);
		GPlayer player = playerAttrib.get();
		if (player == null) {
			player = new GPlayer();
			player.setChannelContext(channelHandlerContext);
			playerAttrib.set(player);
		}
		try {
			cubeWorldPacket.receivedFrom(player);
		} catch (IllegalAccessError e) {
			PacketType packet = (PacketType) cubeWorldPacket.getClass().getAnnotation(PacketType.class);
			Glydar.getServer().getLogger().warning("No handler for packet ID " + packet.id());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			GPlayer player = ctx.attr(ProtocolInitializer.PLAYER_ATTRIBUTE_KEY).get();

			if (player != null) {
				Glydar.getServer().getLogger().info("Player " + player.getName() + " (ID: "
						+ player.getEntityId() + ")" + " has disconnected!");
				player.playerLeft();
			}
		}
	}
}
