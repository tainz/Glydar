package org.glydar.glydar.protocol.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;

import java.io.IOException;
import java.util.logging.Level;

public class ProtocolHandler extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext context, Packet packet) throws Exception {
		try {
			channelRead1(context, packet);
		}
		catch (IOException exc) {
			throw new IOException(exc);
		}
		catch (Exception exc) {
			throw new ProtocolHandlerException(exc, packet);
		}
	}

	private void channelRead1(ChannelHandlerContext context, Packet packet) throws Exception {
		Attribute<GPlayer> playerAttrib = context.attr(ProtocolInitializer.PLAYER_ATTRIBUTE_KEY);
		GPlayer player = playerAttrib.get();
		if (player == null) {
			player = new GPlayer();
			player.setChannelContext(context);
			playerAttrib.set(player);
		}
		try {
			packet.receivedFrom(player);
		} catch (UnsupportedOperationException exc) {
			Glydar.getServer().getLogger().warning("No handler for packet " + packet.getPacketType());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// In case things go reaallly wrong.
		try {
			exceptionCaught0(ctx, cause);
		}
		catch (Exception exc) {
			Glydar.getServer().getLogger().log(Level.SEVERE, "Error while handling error in " + getClass().getCanonicalName(), exc);
		}
	}

	private void exceptionCaught0(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		GPlayer player = ctx.attr(ProtocolInitializer.PLAYER_ATTRIBUTE_KEY).get();

		if (cause instanceof IOException) {
			if (player != null) {
				Glydar.getServer().getLogger().info("Player " + player.getName() + " (ID: "
						+ player.getEntityId() + ") has disconnected!");
				player.playerLeft();
			}
		} else if (cause instanceof ProtocolHandlerException) {
			Packet packet = ((ProtocolHandlerException) cause).getPacket();
			Glydar.getServer().getLogger().log(Level.WARNING,
					"Error while handling packet " + packet.getPacketType() + " for player " + player.getName() + " (ID: "
					+ player.getEntityId() + ")", cause.getCause());
		} else {
			Glydar.getServer().getLogger().log(Level.SEVERE, "Unexpected error while handling packet in " + getClass().getCanonicalName(), cause);
		}
	}
}
