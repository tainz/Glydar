package org.glydar.glydar.models;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.data.GVector3;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;

public abstract class GEntity implements Entity {
	public final long entityID;
	protected GEntityData data;
	protected GWorld world;

	public GEntity() {
		entityID = GEntity.getNewEntityID();
		if (!(this instanceof GPlayer)) {
			Glydar.getServer().addEntity(entityID, this);
		}
		world = (GWorld) Glydar.getServer().getDefaultWorld();
		world.addEntity(entityID, this);
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
	public void forceUpdateData() {
		data.setBitSet(GEntityData.FULL_BITMASK);
		new Packet0EntityUpdate(this.data).sendToWorld(world);
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
	
	public World getWorld(){
		return world;
	}
	
	public void changeWorld(World w){
		//Temporary (?) way of removing the model from world
		for (Player p : w.getWorldPlayers()) {
			GEntityData ed = new GEntityData(this.getEntityData());
			ed.setHostileType((byte) 2);
			GVector3<Long> v = new GVector3<Long>();
			v.setX((long) 0);
			v.setY((long) 0);
			v.setZ((long) 0);
			ed.setPosition(v);
			new Packet0EntityUpdate(ed).sendTo(p);
		}
		
		world.removeEntity(entityID);
		world = (GWorld) w;
		world.addEntity(entityID, this);
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
