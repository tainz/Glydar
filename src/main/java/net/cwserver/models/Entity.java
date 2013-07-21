package net.cwserver.models;

public class Entity {
	private static long ENTITY_ID = 1;
	public static long getEntityID() {
		return ENTITY_ID++;
	}
}
