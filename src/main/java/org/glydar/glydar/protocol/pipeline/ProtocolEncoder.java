package org.glydar.glydar.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteOrder;

import org.glydar.glydar.protocol.Packet;

public class ProtocolEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		out = out.order(ByteOrder.LITTLE_ENDIAN);
		out.writeInt(msg.getID());
		if (!msg.getNoData()) {
			msg.encode(out);
		}
	}
}
