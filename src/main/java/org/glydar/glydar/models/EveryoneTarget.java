package org.glydar.glydar.models;

import java.util.Collection;

import org.glydar.api.models.Player;
import org.glydar.glydar.Glydar;

public class EveryoneTarget implements BaseTarget {
	public static final EveryoneTarget INSTANCE = new EveryoneTarget();

	private EveryoneTarget() {};

	@Override
	public Collection<Player> getPlayers() {
		return Glydar.getServer().getConnectedPlayers();
	}
}
