package net.cwserver.models;

import java.util.HashSet;
import java.util.Set;

public class Player implements BaseTarget {
	@Override
	public Set<Player> getPlayers() {
		Set<Player> ret = new HashSet<Player>();
		ret.add(this);
		return ret;
	}
}
