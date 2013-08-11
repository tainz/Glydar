package org.glydar.glydar.models;

import java.util.Collection;

public class EveryoneTarget implements BaseTarget {
	public static final EveryoneTarget INSTANCE = new EveryoneTarget();

	private EveryoneTarget() { }

	@Override
	public Collection<GPlayer> getPlayers() {
		return GPlayer.getConnectedPlayers();
	}
}
