package org.glydar.paraglydar.models;

import java.util.Collection;

public class EveryoneTarget implements BaseTarget {
	public static final EveryoneTarget INSTANCE = new EveryoneTarget();

	private EveryoneTarget() { }

	@Override
	public Collection<Player> getPlayers() {
		return Player.getConnectedPlayers();
	}
}
