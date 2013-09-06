package org.glydar.glydar.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import org.glydar.glydar.protocol.Packet;

import java.nio.ByteOrder;
import java.util.List;

public class ProtocolDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {
		byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
		int packetID = byteBuf.readInt();
		if (packetID > 20) {
			channelHandlerContext.channel().close();
		}
		Packet packet = Packet.getByID(packetID);
		packet.decode(byteBuf);
		objects.add(packet);
	}
}
