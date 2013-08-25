package org.glydar.api.event.events;

import java.util.Collection;
import org.glydar.api.data.EntityData;
import org.glydar.api.event.Event;
import org.glydar.api.models.Player;
import org.glydar.glydar.models.BaseTarget;
import org.glydar.glydar.models.CustomTarget;
import org.glydar.glydar.models.EveryoneTarget;

public class EntityUpdateEvent extends Event {

	private boolean cancelled = false;
	private Player player;
	private EntityData ed;
	private BaseTarget recievers = EveryoneTarget.INSTANCE;

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
	
	public BaseTarget getTarget(){
		return recievers;
	}
	
	public Collection<Player> getRecievers(){
		return recievers.getPlayers();
	}
	
	public void setRecievers(Collection<Player> c){
		recievers = new CustomTarget(c);
	}
}
