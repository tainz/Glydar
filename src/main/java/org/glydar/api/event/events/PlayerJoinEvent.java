package org.glydar.api.event.events;

import java.util.ArrayList;
import java.util.Collection;

import org.glydar.api.event.Event;
import org.glydar.api.models.Player;
import org.glydar.glydar.models.BaseTarget;
import org.glydar.glydar.models.CustomTarget;
import org.glydar.glydar.models.EveryoneTarget;
import org.glydar.glydar.models.GPlayer;

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
