package org.glydar.glydar.models;

import java.util.Collection;

public class CustomTarget implements BaseTarget {
	Collection<GPlayer> players;
	
	public CustomTarget(Collection<GPlayer> players){
		this.players = players;
	}
	
	@Override
	public Collection<GPlayer> getPlayers() {
		return players;
	}

}
