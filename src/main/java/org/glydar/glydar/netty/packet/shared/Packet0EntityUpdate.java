package org.glydar.glydar.netty.packet.shared;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.glydar.glydar.models.Player;
import org.glydar.glydar.netty.data.EntityData;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.util.ZLibOperations;

import java.nio.ByteOrder;

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
        if(!ply.joined) {
            try {
                EntityData ed = new EntityData();
                ByteBuf dataBuf = Unpooled.copiedBuffer(ZLibOperations.decompress(this.rawData));
                dataBuf.order(ByteOrder.LITTLE_ENDIAN);
                ed.decode(dataBuf);
                System.out.println("Entity ID "+ply.entityID+" name "+ed.name+" identifies as ID "+ed.id); //ID is wrong?
                ply.data = ed;
            } catch (Exception e) { e.printStackTrace(); }
        }
        ply.playerJoined();
		this.sendToAll();
    }

    @Override
    protected void internalEncode(ByteBuf buf) {
        buf.writeInt(rawData.length);
        buf.writeBytes(rawData);
    }
}
