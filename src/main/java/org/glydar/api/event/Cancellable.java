package org.glydar.api.event;

/**
 *
 * @author YoshiGenius
 */
public interface Cancellable {
    
    public boolean isCancelled();
    
    public void setCancelled(boolean cancelled);

}
