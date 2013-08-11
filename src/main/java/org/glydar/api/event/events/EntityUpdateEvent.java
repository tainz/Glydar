package org.glydar.glydar.event.events;

import org.glydar.glydar.api.data.EntityData;
import org.glydar.glydar.api.models.Player;
import org.glydar.glydar.event.Event;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.data.GEntityData;

public class EntityUpdateEvent extends Event {

	private boolean cancelled = false;
	private Player player;
	private EntityData ed;

	public EntityUpdateEvent(final Player player, final EntityData ed) {
		this.setPlayer(player);
		this.setEntityData(ed);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public EntityData getEntityData(){
		return ed;
	}
	
	public void setEntityData(EntityData ed){
		this.ed = ed;
	}
}
