package org.glydar.api.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventSupport<E extends Event> {

    private final List<StoredHandler<E>> handleHandler;
    private final List<StoredHandler<E>> monitorHandler;

    public EventSupport() {
        this.handleHandler = new ArrayList<>();
        this.monitorHandler = new ArrayList<>();
    }

    List<StoredHandler<E>> getHandlers(EventPhase phase) {
        switch (phase) {
        case HANDLE:
            return handleHandler;
        case MONITOR:
            return monitorHandler;
        }

        throw new UnsupportedOperationException(); 
    }

    void addHandler(EventPhase phase, StoredHandler<E> handler) {
        List<StoredHandler<E>> handlers = getHandlers(phase);
        handlers.add(handler);
        Collections.sort(handlers);
    }
}
