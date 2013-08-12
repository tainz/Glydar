package org.glydar.glydar.netty.data.actions;

import org.glydar.glydar.netty.data.BaseData;
import org.glydar.glydar.netty.data.GVector3;

import io.netty.buffer.ByteBuf;

public class GSoundAction implements BaseData{

	GVector3 position;
	int soundType;
	float pitch;
	float volume;
	
	@Override
	public void decode(ByteBuf buf) {
		position = new GVector3();
		position.decode(buf);
		soundType = buf.readInt();
		pitch = buf.readFloat();
		volume = buf.readFloat();
	}

	@Override
	public void encode(ByteBuf buf) {
		position.encode(buf);
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
