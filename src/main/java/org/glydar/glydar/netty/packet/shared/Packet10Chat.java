package org.glydar.glydar.netty.packet.shared;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.Entity;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 10, variableLength = true)
public class Packet10Chat extends CubeWorldPacket {
    int length;
    byte[] messageBytes;
    String message;
	Entity sender;

	//DO NOT EVER TURN ON RECEIVING CACHING. THE SIGNATURES DIFFER!!!

	public Packet10Chat() {

	}

	public Packet10Chat(String message, Entity sender) {
		this.sender = sender;
		this.message = message;
	}

    @Override
    protected void internalDecode(ByteBuf buf) {
        length = buf.readInt();
        messageBytes = new byte[length * 2];
        buf.readBytes(messageBytes);
        message = new String(messageBytes, Charsets.UTF_16LE);
    }

	@Override
	protected void internalEncode(ByteBuf buf) {
		byte[] msgBuf = message.getBytes(Charsets.UTF_16LE);
		buf.writeLong(sender.entityID);
		//TODO Force string encoding.
		buf.writeInt(msgBuf.length / 2);
		buf.writeBytes(msgBuf);
	}

    @Override
    public void receivedFrom(Player ply) {
		sender = ply;
		new Packet10Chat(message, ply).sendToAll();
        Glydar.getServer().getLogger().info("(Chat) <"+ply.data.name+"> "+message);
    }
}
