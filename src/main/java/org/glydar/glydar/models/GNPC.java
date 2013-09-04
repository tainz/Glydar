package org.glydar.glydar.models;

import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.paraglydar.models.NPC;

public class GNPC extends GEntity implements NPC {

	public GNPC() {
		super();
		data = new GEntityData();
		data.setId(entityID);
	}
}
