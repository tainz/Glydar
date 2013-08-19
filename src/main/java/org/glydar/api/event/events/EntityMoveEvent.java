package org.glydar.api.event.events;

import org.glydar.api.data.EntityData;
import org.glydar.api.models.Player;

public class EntityMoveEvent extends EntityUpdateEvent {
	
	public EntityMoveEvent(final Player player, final EntityData ed) {
		super(player, ed);
	}

}
