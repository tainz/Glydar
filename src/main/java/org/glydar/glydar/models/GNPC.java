package org.glydar.glydar.models;

import org.glydar.glydar.protocol.data.GEntityData;
import org.glydar.paraglydar.models.NPC;

public class GNPC extends GEntity implements NPC {

	public GNPC() {
		super();
		data = new GEntityData();
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
	
}
