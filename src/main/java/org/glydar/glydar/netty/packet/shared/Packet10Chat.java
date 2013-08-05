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
    long senderID;

	//DO NOT EVER TURN ON RECEIVING CACHING. THE SIGNATURES DIFFER!!!

    public Packet10Chat() { //Needed for the package finder

    }

	public Packet10Chat(String message, Entity sender) {
		this.sender = sender;
        this.senderID = sender.entityID;
		this.message = message;
	}

    /**
     * Creates a new chat packet instance, ready to be sent to clients.
     * @param message Message to be sent
     * @param senderID Entity ID of the sender or 0 if generic server message.
     */
    public Packet10Chat(String message, int senderID) {
        this.message = message;
        this.senderID = senderID;
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
		buf.writeLong(senderID);
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
