package org.glydar.api.event.events;

import org.glydar.api.event.Cancellable;
import org.glydar.api.event.Event;
import org.glydar.api.event.impl.EventSupport;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

/**
 * @author YoshiGenius
 */
public class PacketEvent extends Event implements Cancellable {
    
    private static EventSupport<PacketEvent> EVENT_SUPPORT = new EventSupport<>();

    private boolean cancelled;
    private final CubeWorldPacket packet;
    
    public PacketEvent(CubeWorldPacket packet) {
        this.cancelled = false;
        this.packet = packet;
        
    }
    
    public CubeWorldPacket getPacket() {
        return this.packet;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public EventSupport<?> getEventSupport() {
        return EVENT_SUPPORT;
    }
}