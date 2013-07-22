package net.cwserver.netty.packet.client;

import io.netty.buffer.ByteBuf;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 9)
public class Packet9ShootArrow extends CubeWorldPacket {
    long entID; //Unsigned!

    int chunkX;
    int chunkY;

    long something5; //Unsigned Int!

    long posX;
    long posY;
    long posZ;

    float something13; //uint
    float something14; //uint
    float something15; //uint

    float velX;
    float velY;
    float velZ;

    float something19; // rand() ??
    float something20;
    float something21;
    float something22; // ?????
    long something23; //uint
    byte something24;
    long something25; //uint
    byte something26;
    long something27; //uint
    long something28; //uint

    @Override
    protected void internalDecode(ByteBuf buf) {
        entID = buf.readUnsignedInt(); //Unsigned long actually!

        chunkX = buf.readInt();
        chunkY = buf.readInt();

        something5 = buf.readUnsignedInt();
        buf.readBytes(4); //Padding

        posX = buf.readLong();
        posY = buf.readLong();
        posZ = buf.readLong();

        something13 = buf.readUnsignedInt();
        something14 = buf.readUnsignedInt();
        something15 = buf.readUnsignedInt();

        velX = buf.readFloat();
        velY = buf.readFloat();
        velZ = buf.readFloat();

        something19 = buf.readFloat();
        something20 = buf.readFloat();
        something21 = buf.readFloat();
        something22 = buf.readFloat();
        something23 = buf.readUnsignedInt();
        something24 = buf.readByte();
        buf.readBytes(3); //Padding
        something25 = buf.readUnsignedInt();
        something26 = buf.readByte();
        buf.readBytes(3); //Padding
        something27 = buf.readUnsignedInt();
        something28 = buf.readUnsignedInt();




    }
}
