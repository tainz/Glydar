package org.glydar.api.data;

import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.data.GVector3;

public class DataAPI {

	//Creating Vectors
	public static Vector3 Vector3(){
		return new GVector3<Float>();
	}
	
	public static Vector3 Vector3(float x, float y, float z){
		GVector3<Float> v = new GVector3<Float>();
		v.setX(x);
		v.setY(y);
		v.setZ(z);
		return v;
	}
	
	public static <T extends Number> Vector3 Vector3(Class<T> type){
		if (Long.class.isAssignableFrom(type)){
			return new GVector3<Long>();
		} else if (Integer.class.isAssignableFrom(type)) {
			return new GVector3<Integer>();
		} else {
			return new GVector3<Float>();
		}
	}
	
	public static <T extends Number> Vector3 Vector3(T x, T y, T z){
		if (Long.class.isAssignableFrom(x.getClass())){
			GVector3<Long> v = new GVector3<Long>();
			v.setX((Long) x);
			v.setY((Long) x);
			v.setZ((Long) x);
			return v;
		} else if (Integer.class.isAssignableFrom(x.getClass())) {
			GVector3<Integer> v = new GVector3<Integer>();
			v.setX((Integer) x);
			v.setY((Integer) x);
			v.setZ((Integer) x);
			return v;
		} else {
			GVector3<Float> v = new GVector3<Float>();
			v.setX((Float) x);
			v.setY((Float) x);
			v.setZ((Float) x);
			return v;
		}
	}
	
	public static Vector3 Vector3(Vector3 v){
		return new GVector3(v);
	}
	
	//Creating EntityData
	public static EntityData EntityData(){
		return new GEntityData();
	}
	
	public static EntityData EntityData(EntityData e){
		return new GEntityData(e);
	}
}
