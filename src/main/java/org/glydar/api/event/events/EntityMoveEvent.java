package org.glydar.api.event.events;

import org.glydar.api.data.EntityData;
import org.glydar.api.event.Event;
import org.glydar.api.models.Player;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.data.GEntityData;

public class EntityMoveEvent extends EntityUpdateEvent {
	
	public EntityMoveEvent(final Player player, final EntityData ed) {
		super(player, ed);
	}

}
