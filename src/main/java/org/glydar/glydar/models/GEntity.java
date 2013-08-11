package org.glydar.glydar.models;

import org.glydar.glydar.api.models.Entity;

public class GEntity implements Entity{
	public final long entityID;

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
