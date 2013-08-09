package org.glydar.glydar.event.events;

import org.glydar.glydar.event.Event;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.netty.data.EntityData;

public class EntityHealthEvent extends EntityUpdateEvent {

	public EntityHealthEvent(final Player player, final EntityData ed) {
		super(player, ed);
	}
}
