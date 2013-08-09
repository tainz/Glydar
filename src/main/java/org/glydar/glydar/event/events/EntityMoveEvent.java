package org.glydar.glydar.event.events;

import org.glydar.glydar.event.Event;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.netty.data.EntityData;

public class EntityMoveEvent extends EntityUpdateEvent {
	
	public EntityMoveEvent(final Player player, final EntityData ed) {
		super(player, ed);
	}

}
