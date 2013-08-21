package org.glydar.api.event.events;

import org.glydar.api.event.Event;
import org.glydar.api.models.Player;

public class PlayerJoinEvent extends Event {

	private Player player;
	private String joinMessage = "Player " + player.getName() + " (ID: " + player.getEntityId() + ") has joined the server.";

	public PlayerJoinEvent(final Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
	
	public String getJoinMessage(){
		return joinMessage;
	}

	public void setJoinMessage(String m){
		this.joinMessage = m;
	}
}
