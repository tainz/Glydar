package org.glydar.glydar.netty.data;

import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.DataCreator;
import org.glydar.paraglydar.data.Vector3;
import org.glydar.paraglydar.data.EntityData;

public class GDataCreator implements DataCreator {

	public GDataCreator() {};

	//Creating Vectors
	public Vector3<Float> createVector3(float x, float y, float z) {
		GVector3<Float> v = new GVector3<Float>();
		v.setX(x);
		v.setY(y);
		v.setZ(z);
		return v;
	}

	public <T extends Number> Vector3<T> createVector3(Class<T> type) {
		if (Long.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type)) {
			return new GVector3<T>();
		} else {
			return null;
		}

	}

	public <T extends Number> Vector3<T> createVector3(T x, T y, T z) {
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
		return new GVector3<T>(v);
	}

	//Creating EntityData
	public EntityData createEntityData() {
		return new GEntityData();
	}

	public EntityData createEntityData(EntityData e) {
		return new GEntityData(e);
	}
	
	//Creating Appearance Data
	public Appearance createAppearance() {
		return new GAppearance();
	}

	public Appearance createAppearance(Appearance a) {
		return new GAppearance(a);
	}
}
