package org.glydar.glydar.netty.data;

import org.glydar.glydar.Glydar;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.DataCreator;
import org.glydar.paraglydar.data.EntityData;

public class GDataCreator implements DataCreator {

	public GDataCreator() {};

	//Creating EntityData
	public EntityData createEntityData() {
		Glydar.getServer().debug("Creating EntityData");
		return new GEntityData();
	}

	public EntityData createEntityData(EntityData e) {
		Glydar.getServer().debug("Creating EntityData with base of " + e.toString());
		return new GEntityData(e);
	}
	
	//Creating Appearance Data
	public Appearance createAppearance() {
		Glydar.getServer().debug("Creating Appearance");
		return new GAppearance();
	}

	public Appearance createAppearance(Appearance a) {
		Glydar.getServer().debug("Creating Appearance with base of " + a.toString());
		return new GAppearance(a);
	}
}
