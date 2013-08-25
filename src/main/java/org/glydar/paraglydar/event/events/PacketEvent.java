package org.glydar.api.event.events;

import org.glydar.api.event.Cancellable;
import org.glydar.api.event.Event;
import org.glydar.glydar.netty.packet.CubeWorldPacket;

/**
 * @author YoshiGenius
 */
public class PacketEvent extends Event implements Cancellable {
    
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

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}