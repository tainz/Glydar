package org.glydar.glydar.netty.data.actions;

import static org.glydar.glydar.util.VectorBuf.readFloatVector3;
import static org.glydar.glydar.util.VectorBuf.writeFloatVector3;
 
import org.glydar.glydar.netty.data.BaseData;
import org.glydar.paraglydar.geom.FloatVector3;

import io.netty.buffer.ByteBuf;

public class GSoundAction implements BaseData {

	FloatVector3 position;
	int soundType;
	float pitch;
	float volume;

	public GSoundAction() {
		position = new FloatVector3();
	}

	@Override
	public void decode(ByteBuf buf) {
		position = readFloatVector3(buf);
		soundType = buf.readInt();
		pitch = buf.readFloat();
		volume = buf.readFloat();
	}

	@Override
	public void encode(ByteBuf buf) {
		writeFloatVector3(buf, position);
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

		private SoundType(int index) {
			this.index = index;
		}

		public SoundType getSoundByNumber(int i) {
			for (SoundType st : SoundType.values()) {
				if (st.index == i) {
					return st;
				}
			}
			return null;
		}
	}
}
