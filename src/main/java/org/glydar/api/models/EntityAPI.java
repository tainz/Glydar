package org.glydar.api.models;

import org.glydar.api.data.EntityData;
import org.glydar.glydar.models.GEntity;
import org.glydar.glydar.netty.data.GEntityData;

public class EntityAPI {

	//Creating Entities
	public static Entity Entity(){
		return new GEntity();
	}
	
	public static Entity Entity(EntityData e){
		GEntity g = new GEntity();
		GEntityData d = new GEntityData(e);
		g.setEntityData(d);
		return g;
	}
}
