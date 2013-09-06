package org.glydar.glydar.protocol.shared;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GEntity;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;
import org.glydar.paraglydar.event.events.ChatEvent;
import org.glydar.paraglydar.models.BaseTarget;
import org.glydar.paraglydar.models.WorldTarget;

@PacketType(id = 10, variableLength = true)
public class Packet10Chat extends Packet {
	int length;
	byte[] messageBytes;
	String message;
	GEntity sender;
	long senderID;
	boolean cancelled;
	BaseTarget target;

	public Packet10Chat() {}

	public Packet10Chat(String message, GEntity sender) {
		this.sender = sender;
		this.senderID = sender.entityID;
		this.message = message;
		target = new WorldTarget(sender.getWorld());
	}

	/**
	 * Creates a new chat packet instance, ready to be sent to clients.
	 *
	 * @param message  Message to be sent
	 * @param senderID Entity ID of the sender or 0 if generic server message.
	 */
	public Packet10Chat(String message, long senderID) {
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
		if (!isCmd) {
			manageChatEvent(ply);
			if (!cancelled) {
				new Packet10Chat(message, ply).sendTo(target);
				Glydar.getServer().getLogger().info("(Chat) <" + ply.getEntityData().getName() + "> " + message);
			}
		}
	}

	public boolean manageCommands(GPlayer ply) {
		if (message.startsWith("/")) {
			Glydar.getServer().getCommandManager().execute(ply, message.substring(1));
			return true;
		}
		return false;
	}

	public void manageChatEvent(GPlayer ply) {
		ChatEvent event = Glydar.getEventManager().callEvent(new ChatEvent(ply, message));
		message = event.getMessage();
		target = event.getTarget();
		if (event.isCancelled()) {
			cancelled = true;
		}
	}
}
