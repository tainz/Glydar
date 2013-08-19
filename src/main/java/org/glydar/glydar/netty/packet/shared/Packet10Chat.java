package org.glydar.glydar.netty.packet.shared;

import java.util.Arrays;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import org.glydar.api.command.CommandManager;
import org.glydar.api.event.EventManager;
import org.glydar.api.event.events.ChatEvent;
import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.BaseTarget;
import org.glydar.glydar.models.EveryoneTarget;
import org.glydar.glydar.models.GEntity;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 10, variableLength = true)
public class Packet10Chat extends CubeWorldPacket {
    int length;
    byte[] messageBytes;
    String message;
	GEntity sender;
    long senderID;
    boolean cancelled;
    BaseTarget target = EveryoneTarget.INSTANCE;

	//DO NOT EVER TURN ON RECEIVING CACHING. THE SIGNATURES DIFFER!!!

    public Packet10Chat() { //Needed for the package finder

    }

	public Packet10Chat(String message, GEntity sender) {
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
    public void receivedFrom(GPlayer ply) {
		sender = ply;
		boolean isCmd = manageCommands(ply);
		if (!isCmd){
			manageChatEvent(ply);
			if (!cancelled){
				new Packet10Chat(message, ply).sendTo(target);
		        Glydar.getServer().getLogger().info("(Chat) <"+ply.getEntityData().getName()+"> "+message);
			}
		}
    }
    
    public boolean manageCommands(GPlayer ply){
    	if (message.startsWith("/")){
    		String command = message.substring(1);
    		String[] parts = command.split(" ");
    		String lbl = parts[0];
    		String[] args;
    		if (parts.length > 2){
    			args = Arrays.copyOfRange(parts, 1, parts.length - 1);
    		} else {
    			args = null;
    		}
    		CommandManager.exec(ply, lbl, args);
    		return true;
    	}
    	return false;
    }
    
    public void manageChatEvent(GPlayer ply){
    	ChatEvent event = (ChatEvent) EventManager.callEvent(new ChatEvent(ply, message));
    	message = event.getMessage();
    	target = event.getTarget();
    	if (event.isCancelled()){
    			cancelled = true;
    	}
    }
}
