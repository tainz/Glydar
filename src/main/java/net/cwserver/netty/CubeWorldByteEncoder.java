package net.cwserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.cwserver.netty.packet.CubeWorldPacket;

import java.nio.ByteOrder;

public class CubeWorldByteEncoder extends MessageToByteEncoder<CubeWorldPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CubeWorldPacket msg, ByteBuf out) throws Exception {
        out = out.order(ByteOrder.LITTLE_ENDIAN);
        out.writeInt(msg.getID());
        msg.encode(out);
    }
}
