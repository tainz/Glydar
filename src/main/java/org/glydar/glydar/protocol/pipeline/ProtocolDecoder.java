package org.glydar.glydar.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;

import java.nio.ByteOrder;
import java.util.List;

public class ProtocolDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf buf, List<Object> objects) {
		buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		int packetId = buf.readInt();
		PacketType type = PacketType.valueOf(packetId);
		Packet packet = type.createPacket(buf);
		objects.add(packet);
	}
}
