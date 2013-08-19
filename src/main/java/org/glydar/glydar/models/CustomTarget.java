package org.glydar.glydar.models;

import java.util.Collection;

import org.glydar.api.models.Player;

public class CustomTarget implements BaseTarget {
	Collection<Player> players;
	
	public CustomTarget(Collection<Player> players){
		this.players = players;
	}
	
	@Override
	public Collection<Player> getPlayers() {
		return players;
	}

}
