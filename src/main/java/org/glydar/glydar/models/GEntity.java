package org.glydar.glydar.models;

public class Entity {
	public final long entityID;

	public Entity() {
		entityID = Entity.getNewEntityID();
	}

	protected Entity(int forceID) {
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

		Entity entity = (Entity) o;

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
