package org.glydar.api.event;

public interface EventManager {

    void register(Listener listener);

    <E extends Event> E callEvent(E event);
}