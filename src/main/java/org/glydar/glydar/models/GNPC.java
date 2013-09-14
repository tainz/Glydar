package org.glydar.glydar.models;

import org.glydar.glydar.Glydar;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.models.NPC;

public class GNPC extends GEntity implements NPC {

	public GNPC() {
		super();
		data = new EntityData();
		data.setId(entityID);
		data.setEntity(this);
	}

	@Override
	public void kill() {
		setHealth(0F);
	}

	@Override
	public void heal(float health) {
		setHealth(data.getHP() + health);
	}

	@Override
	public void setHealth(float health) {
		data.setHP(health);
		forceUpdateData(true);
	}

	@Override
	public void damage(float damage) {
		setHealth(data.getHP() - damage);
	}
	
	@Override
	public void entityDiedPVP(){
		super.entityDiedPVP();
		Glydar.getServer().removeEntity(entityID);
		world.removeEntity(entityID);
	}
}
