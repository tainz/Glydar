package org.glydar.api.event;

public interface EventManager {

    void register(Listener listener);

    <E extends Event> void register(EventPhase phase, EventOrder order, Class<E> eventClass, EventExecutor<E> executor);

    <E extends Event> E callEvent(E event);
}