package org.glydar.api.event;

public interface EventExecutor<E extends Event> {

    void execute(E event);
}
