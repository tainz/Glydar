package net.cwserver.models;

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
}
