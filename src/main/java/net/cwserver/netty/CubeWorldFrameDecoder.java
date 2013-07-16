package net.cwserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.cwserver.netty.packet.CubeWorldPacket;

public class CubeWorldFrameDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, MessageList<Object> objects) throws Exception {
		int packetID = byteBuf.readInt();
		CubeWorldPacket packet = CubeWorldPacket.getByID(packetID);
		//channelHandlerContext.attr(CubeWorldServerInitializer.PLAYER_ATTRIBUTE_KEY).get()
		packet.decode(byteBuf);
		objects.add(packet);
	}
}
