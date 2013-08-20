package org.glydar.api.event.impl;

import org.glydar.api.event.Event;
import org.glydar.api.event.EventExecutor;
import org.glydar.api.event.EventOrder;

public class StoredHandler<E extends Event> implements Comparable<StoredHandler<E>> {

    private final EventOrder order;
    private final EventExecutor<E> executor;

    public StoredHandler(EventOrder order, EventExecutor<E> executor) {
        this.order = order;
        this.executor = executor;
    }

    public void execute(E event) {
        executor.execute(event);
    }

    @Override
    public int compareTo(StoredHandler<E> other) {
        return order.ordinal() - other.order.ordinal();
    }
}
