package org.glydar.glydar.models;

import java.util.Collection;

import org.glydar.glydar.Glydar;

public class EveryoneTarget implements BaseTarget {
	public static final EveryoneTarget INSTANCE = new EveryoneTarget();

	private EveryoneTarget() { }

	@Override
	public Collection<GPlayer> getPlayers() {
		return Glydar.getServer().getConnectedPlayers();
	}
}
