package org.glydar.glydar.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.handler.codec.ReplayingDecoder;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

import java.nio.ByteOrder;

public class CubeWorldFrameDecoder extends ReplayingDecoder {
	@Override
	public boolean isSingleDecode() {
		return true;
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, MessageList<Object> objects) throws Exception {
		byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
		int packetID = byteBuf.readInt();
		if (packetID > 20) {
			System.out.println("WTF?!");
			channelHandlerContext.channel().close();
		}
		CubeWorldPacket packet = CubeWorldPacket.getByID(packetID);
		packet.decode(byteBuf);
		objects.add(packet);
	}
}
