package org.glydar.api.event;

import org.glydar.api.event.impl.EventSupport;


/**
 * Base event object
 * @author YoshiGenius
 */
public abstract class Event {

    public abstract EventSupport<?> getEventSupport();
}