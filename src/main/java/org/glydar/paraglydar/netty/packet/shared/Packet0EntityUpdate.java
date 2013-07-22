package org.glydar.paraglydar.netty.packet.shared;

import io.netty.buffer.ByteBuf;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 0, variableLength = true)
public class Packet0EntityUpdate extends CubeWorldPacket {
	 byte[] rawData;

	@Override
	protected boolean doCacheIncoming() {
		return true;
	}

	@Override
	protected void internalDecode(ByteBuf buffer) {
		int zlibLength = buffer.readInt();
		rawData = new byte[zlibLength];
		buffer.readBytes(rawData);
	}

    @Override
    public void receivedFrom(Player ply) {
        ply.playerJoined();
		this.sendToAll();
    }

    @Override
    protected void internalEncode(ByteBuf buf) {
        buf.writeInt(rawData.length);
        buf.writeBytes(rawData);
    }
}
