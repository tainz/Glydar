package org.glydar.paraglydar.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.glydar.paraglydar.netty.packet.CubeWorldPacket;

import java.nio.ByteOrder;

public class CubeWorldByteEncoder extends MessageToByteEncoder<CubeWorldPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CubeWorldPacket msg, ByteBuf out) throws Exception {
        out = out.order(ByteOrder.LITTLE_ENDIAN);
        out.writeInt(msg.getID());
        msg.encode(out);
    }
}
