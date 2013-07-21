package net.cwserver.netty.packet.shared;

import io.netty.buffer.ByteBuf;
import net.cwserver.models.Player;
import net.cwserver.netty.packet.CubeWorldPacket;

@CubeWorldPacket.Packet(id = 0, variableLength = true)
public class Packet0EntityUpdate extends CubeWorldPacket {
	 byte[] rawData;

	@Override
	public void decode(ByteBuf buffer) {
		int zlibLength = buffer.readInt();
		rawData = new byte[zlibLength];
		buffer.readBytes(rawData);
	}

    @Override
    public void receivedFrom(Player ply) {
        for (Player p : ply.getPlayers()) {
            p.getChannelContext().write(this);
        }
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(rawData.length);
        buf.writeBytes(rawData);
    }
}
