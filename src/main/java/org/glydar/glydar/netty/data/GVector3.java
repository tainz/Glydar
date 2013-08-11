package org.glydar.glydar.netty.data;

import io.netty.buffer.ByteBuf;

public class Vector3 implements BaseData {
    public float x;
    public float y;
    public float z;

    @Override
    public void decode(ByteBuf buf) {
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
    }
}
