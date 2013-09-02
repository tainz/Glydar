package org.glydar.glydar.netty.data;

import org.glydar.glydar.Glydar;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.DataCreator;
import org.glydar.paraglydar.data.Vector3;
import org.glydar.paraglydar.data.EntityData;

public class GDataCreator implements DataCreator {

	public GDataCreator() {};

	//Creating Vectors
	public Vector3<Float> createVector3(float x, float y, float z) {
		Glydar.getServer().debug("Creating Vector3<Float> with " + x + ", " + y + ", " + z);
		GVector3<Float> v = new GVector3<Float>();
		v.setX(x);
		v.setY(y);
		v.setZ(z);
		return v;
	}

	public <T extends Number> Vector3<T> createVector3(Class<T> type) {
		Glydar.getServer().debug("Creating Vector3<T> with type of " + type.toString());
		if (Long.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type)) {
			return new GVector3<T>();
		} else {
			return null;
		}

	}

	public <T extends Number> Vector3<T> createVector3(T x, T y, T z) {
		Glydar.getServer().debug("Creating Vector3<T> with " + x + ", " + y + ", " + z);
		if (Long.class.isAssignableFrom(x.getClass()) || Integer.class.isAssignableFrom(x.getClass()) || Float.class.isAssignableFrom(x.getClass())) {
			GVector3<T> v = new GVector3<T>();
			v.setX((T) x);
			v.setY((T) x);
			v.setZ((T) x);
			return v;
		} else {
			return null;
		}
	}

	public <T extends Number> Vector3<T> createVector3(Vector3<T> v) {
		Glydar.getServer().debug("Creating Vector3<T> with base of Vector3: " + v.toString());
		return new GVector3<T>(v);
	}

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
