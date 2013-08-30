package org.glydar.glydar.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

import java.nio.ByteOrder;
import java.util.List;

public class CubeWorldFrameDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {
		byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
		int packetID = byteBuf.readInt();
		if (packetID > 20) {
			channelHandlerContext.channel().close();
		}
		CubeWorldPacket packet = CubeWorldPacket.getByID(packetID);
		packet.decode(byteBuf);
		objects.add(packet);
	}
}
