package net.cwserver.models;

import java.util.Set;

public class EveryoneTarget implements BaseTarget {
	public static final EveryoneTarget INSTANCE = new EveryoneTarget();

	private EveryoneTarget() { }

	@Override
	public Set<Player> getPlayers() {
		return Player.getConnectedPlayers();
	}
}
