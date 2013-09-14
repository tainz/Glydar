package org.glydar.glydar.protocol.data;

import org.glydar.glydar.Glydar;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.DataCreator;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.EntityData;

public class GDataCreator implements DataCreator {

	public GDataCreator() {};

	//Creating EntityData
	public EntityData createEntityData() {
		Glydar.getServer().debug("Creating EntityData");
		return new EntityData();
	}

	public EntityData createEntityData(EntityData e) {
		Glydar.getServer().debug("Creating EntityData with base of " + e.toString());
		return new EntityData(e);
	}
	
	//Creating Appearance Data
	public Appearance createAppearance() {
		Glydar.getServer().debug("Creating Appearance");
		return new Appearance();
	}

	public Appearance createAppearance(Appearance a) {
		Glydar.getServer().debug("Creating Appearance with base of " + a.toString());
		return new Appearance(a);
	}
}
