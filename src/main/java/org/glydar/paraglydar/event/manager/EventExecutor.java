package org.glydar.api.event.manager;

import org.glydar.api.event.Event;

/**
 * Low-level callback called when an Event is fired.
 *
 * @param <E> Type of the expected Event
 */
public interface EventExecutor<E extends Event> {

	void execute(E event);
}
