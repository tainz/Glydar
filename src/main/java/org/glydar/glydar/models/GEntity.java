package org.glydar.glydar.models;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.protocol.data.GEntityData;
import org.glydar.glydar.protocol.shared.Packet0EntityUpdate;
import org.glydar.glydar.protocol.shared.Packet10Chat;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.models.BaseTarget;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.models.WorldTarget;

public abstract class GEntity implements Entity {
	public final long entityID;
	protected GEntityData data;
	protected GWorld world;

	public GEntity() {
		entityID = GEntity.getNewEntityID();
		world = (GWorld) Glydar.getServer().getDefaultWorld();
		if (!(this instanceof GPlayer)) {
			Glydar.getServer().addEntity(entityID, this);
			world.addEntity(entityID, this);
		}
	}

	protected GEntity(int forceID) {
		entityID = forceID;
		world = (GWorld) Glydar.getServer().getDefaultWorld();
	}

	private static long ENTITY_ID = 1;

	public static long getNewEntityID() {
		return ENTITY_ID++;
	}

	/**
	 * Temporary fix to allow plugins to manipulate entityData while we fix other issues.
	 * Call this whenever you modify anything in Player.data and wish to update all of the clients.
	 */
	public void forceUpdateData(boolean full) {
		if (full) data.setBitSet(GEntityData.FULL_BITMASK);
		forceUpdateData();
	}
	
	public void forceUpdateData() {
		new Packet0EntityUpdate(this.data).sendToWorld(world);
	}

	public EntityData getEntityData() {
		if (data == null) {
			data = new GEntityData();
		}
		return data;
	}

	public void setEntityData(EntityData ed) {
		this.data = (GEntityData) ed;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void changeWorld(World w){
		world.removeEntity(entityID);
		world = (GWorld) w;
		world.addEntity(entityID, this);
	}
	
	public void sendMessageFromEntity(BaseTarget target, String message){
		new Packet10Chat(message, entityID).sendTo(target);
	}
	
	public void sendMessageFromEntity(String message){
		sendMessageFromEntity(new WorldTarget(this.getWorld()), message);
	}
	
	public abstract void kill();
	
	public abstract void heal(float health);
	
	public abstract void setHealth(float health);
	
	public abstract void damage(float damage);

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
		return "Entity {type=" + getClass().getSimpleName() + ", id=" + entityID + '}';
	}

	@Override
	public long getEntityId() {
		return entityID;
	}
}
