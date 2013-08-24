package org.glydar.api.event;

/**
 * Describes an Event which can be somehow cancelled
 * by a listener
 *
 * @author YoshiGenius
 */
public interface Cancellable {

	boolean isCancelled();
}
