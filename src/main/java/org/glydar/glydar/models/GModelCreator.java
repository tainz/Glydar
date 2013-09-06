package org.glydar.glydar.models;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.protocol.data.GEntityData;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.models.ModelCreator;
import org.glydar.paraglydar.models.NPC;
import org.glydar.paraglydar.models.World;

public class GModelCreator implements ModelCreator {

	public GModelCreator() {};

	//Creating NPCs
	public NPC createNPC() {
		return new GNPC();
	}

	public NPC createNPC(EntityData e) {
		Glydar.getServer().debug("Creating npc with EntityData: " + e.toString());
		GNPC n = new GNPC();
		GEntityData d = new GEntityData(e);
		n.setEntityData(d);
		n.getEntityData().setId(n.entityID);
		return n;
	}

	@Override
	public World createWorld(String name, int seed) {
		Glydar.getServer().debug("Creating world with name: " + name + " and seed: " + seed);
		return new GWorld(name, seed);
	}
}
