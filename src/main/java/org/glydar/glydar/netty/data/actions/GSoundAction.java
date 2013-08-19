package org.glydar.glydar.netty.data.actions;

import org.glydar.glydar.netty.data.BaseData;
import org.glydar.glydar.netty.data.GVector3;
import io.netty.buffer.ByteBuf;

public class GSoundAction implements BaseData{

	GVector3<Float> position;
	int soundType;
	float pitch;
	float volume;
	
	public GSoundAction() {
		position = new GVector3<Float>();
	}
	
	@Override
	public void decode(ByteBuf buf) {
		position.decode(buf, Float.class);
		soundType = buf.readInt();
		pitch = buf.readFloat();
		volume = buf.readFloat();
	}

	@Override
	public void encode(ByteBuf buf) {
		position.encode(buf, Float.class);
		buf.writeInt(soundType);
		buf.writeFloat(pitch);
		buf.writeFloat(volume);
	}

	//TODO: Finish!
	public enum SoundType {
		HIT(0),
		BLADE1(1),
		BLADE2(2);
		
		private int index;
		private SoundType(int index){
			this.index = index;
		}
		
		public SoundType getSoundByNumber(int i){
			for (SoundType st : SoundType.values()){
				if (st.index == i){
					return st;
				}
			}
			return null;
		}
	}
}
