package org.glydar.glydar.models;

import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.models.ModelCreator;
import org.glydar.paraglydar.models.NPC;

public class GModelCreator implements ModelCreator {
	
	public GModelCreator(){};
	
	//Creating NPCs
	public NPC createNPC(){
		return new GNPC();
	}

	public NPC createNPC(EntityData e){
		GNPC n = new GNPC();
		GEntityData d = new GEntityData(e);
		n.setEntityData(d);
		n.getEntityData().setId(n.entityID);
		return n;
	}
}
