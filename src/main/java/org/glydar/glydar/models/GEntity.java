package org.glydar.glydar.models;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.models.Entity;

public abstract class GEntity implements Entity {
	public final long entityID;
	protected GEntityData data;

	public GEntity() {
		entityID = GEntity.getNewEntityID();
		if (!(this instanceof GPlayer)) {
			Glydar.getServer().addEntity(entityID, this);
		}
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
		data.setBitmask(GEntityData.FULL_BITMASK);
		new Packet0EntityUpdate(this.data).sendToAll();
	}

	public void forceUpdateData(EntityData ed) {
		this.data = (GEntityData) ed;
		forceUpdateData();
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
