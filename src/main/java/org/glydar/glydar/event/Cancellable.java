package org.glydar.glydar.event;

/**
 *
 * @author YoshiGenius
 */
public interface Cancellable {
    
    public boolean isCancelled();
    
    public boolean setCancelled(boolean cancelled);

}
