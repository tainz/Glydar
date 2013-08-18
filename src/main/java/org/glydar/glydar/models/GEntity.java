package org.glydar.glydar.models;

import org.glydar.api.data.EntityData;
import org.glydar.api.models.Entity;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;

public class GEntity implements Entity{
	public final long entityID;
	protected GEntityData data;

	public GEntity() {
		entityID = GEntity.getNewEntityID();
	}

	protected GEntity(int forceID) {
		entityID = forceID;
	}

	private static long ENTITY_ID = 1;
	public static long getNewEntityID() {
		return ENTITY_ID++;
	}
	
	/**
     * Temporary fix to allow plugins to manipulate entityData while we fix other issues.
     * Call this whenever you modify anything in Player.data and wish to update all of the clients.
     */
    public void forceUpdateData() {
        new Packet0EntityUpdate(this.data).sendToAll();
    }
    
    public void forceUpdateData(GEntityData ed){
    	this.data = ed;
    	new Packet0EntityUpdate(this.data).sendToAll();
    }
    
    public EntityData getEntityData(){
    	if (data == null){
    		data = new GEntityData();
    	}
    	return data;
    }

	public void setEntityData(EntityData ed) {
		this.data = (GEntityData) ed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GEntity entity = (GEntity) o;

		if (entityID != entity.entityID) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (entityID ^ (entityID >>> 32));
	}

	@Override
	public String toString() {
		return "Entity {type="+getClass().getSimpleName()+", id="+entityID+'}';
	}
}
