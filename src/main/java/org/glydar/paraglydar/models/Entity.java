package org.glydar.api.models;

import org.glydar.api.data.EntityData;
import org.glydar.glydar.netty.data.GEntityData;

public interface Entity {

	public long getEntityId();
	
	/**
     * Temporary fix to allow plugins to manipulate entityData while we fix other issues.
     * Call this whenever you modify anything in Player.data and wish to update all of the clients.
     */
	public void forceUpdateData();
	    
	public void forceUpdateData(GEntityData ed);
	    
	public EntityData getEntityData();

	public void setEntityData(EntityData ed);
	
	public boolean equals(Object o);

	public int hashCode();

	public String toString();
}
