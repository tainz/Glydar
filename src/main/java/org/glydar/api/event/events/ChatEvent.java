package org.glydar.api.event.events;

import java.util.Collection;
import org.glydar.api.event.Cancellable;
import org.glydar.api.event.Event;
import org.glydar.api.models.Player;
import org.glydar.glydar.models.BaseTarget;
import org.glydar.glydar.models.CustomTarget;
import org.glydar.glydar.models.EveryoneTarget;

public class ChatEvent extends Event implements Cancellable {

	private boolean cancelled = false;
	private Player player;
	private String message;
	private BaseTarget recievers = EveryoneTarget.INSTANCE;

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
	
	public BaseTarget getTarget(){
		return recievers;
	}
	
	public Collection<Player> getRecievers(){
		return recievers.getPlayers();
	}
	
	public void setRecievers(Collection<Player> c){
		recievers = new CustomTarget(c);
	}
}
