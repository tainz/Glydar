package org.glydar.glydar.event.events;

import org.glydar.glydar.event.Cancellable;
import org.glydar.glydar.event.Event;
import org.glydar.glydar.models.Player;

public class ChatEvent extends Event implements Cancellable {

	private boolean cancelled = false;
	private Player player;
	private String message;

	public ChatEvent(final Player player, final String message) {
		this.setPlayer(player);
		this.message = message;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
