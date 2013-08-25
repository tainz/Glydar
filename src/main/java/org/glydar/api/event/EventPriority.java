package org.glydar.api.event;

/**
 * Describes priority of an event handler.
 * <p>
 * Priority are a convention between plugins to avoid incompatibilities as
 * much as possible. Thus, the priority of an handler should be chosen wisely.
 * <p>
 * Handlers are called from LOWEST first to MONITOR last.
 * <p>
 * This order may be confusing at first, but here's the reason why :
 * First thing to understand is that there's not way for an handler to say
 * "Ok, I've handled this one, no others handlers should be notified of it.".
 * In other words, each and every handler (if it wants to) should and will be
 * able to acts on the Event no matters what. Thus, here, the priority is used
 * to determine which handler will most likely be able to have the final say on
 * the event outcome.
 */
public enum EventPriority {

	LOWEST,

	LOW,

	NORMAL,

	HIGH,

	HIGHEST,

	MONITOR;
}
