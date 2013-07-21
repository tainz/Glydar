package net.cwserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.handler.codec.ReplayingDecoder;
import net.cwserver.netty.packet.CubeWorldPacket;

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
        //System.out.println("Caught packet: "+packetID);
		CubeWorldPacket packet = CubeWorldPacket.getByID(packetID);
		packet.decode(byteBuf);
		objects.add(packet);
	}
}
