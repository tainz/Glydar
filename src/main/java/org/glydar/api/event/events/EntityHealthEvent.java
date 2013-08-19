package org.glydar.api.event.events;

import org.glydar.api.data.EntityData;
import org.glydar.api.models.Player;

public class EntityHealthEvent extends EntityUpdateEvent {

	public EntityHealthEvent(final Player player, final EntityData ed) {
		super(player, ed);
	}
}
