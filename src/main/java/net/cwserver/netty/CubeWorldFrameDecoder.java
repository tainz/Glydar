package net.cwserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.Attribute;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.CubeWorldPacket;

import java.nio.ByteOrder;

public class CubeWorldFrameDecoder extends ReplayingDecoder {
	@Override
	public boolean isSingleDecode() {
		return true;
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, MessageList<Object> objects) throws Exception {
		Attribute<Player> playerAttrib = channelHandlerContext.attr(CubeWorldServerInitializer.PLAYER_ATTRIBUTE_KEY);
		Player player = playerAttrib.get();
		if(player == null) {
			player = new Player();
			playerAttrib.set(player);
		}

        byteBuf.order(ByteOrder.LITTLE_ENDIAN);
		int packetID = byteBuf.readInt();
		CubeWorldPacket packet = CubeWorldPacket.getByID(packetID);
		packet.decode(byteBuf);
		packet.receivedFrom(player);
		objects.add(packet);
	}
}
