package org.glydar.glydar.protocol.shared;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.models.GEntity;
import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.protocol.data.DataCodec;
import org.glydar.glydar.protocol.Packet;
import org.glydar.glydar.protocol.PacketType;
import org.glydar.glydar.util.ZLibOperations;
import org.glydar.paraglydar.ParaGlydar;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.event.events.EntityUpdateEvent;
import org.glydar.paraglydar.event.events.PlayerJoinEvent;
import org.glydar.paraglydar.models.Entity;

import java.nio.ByteOrder;

public class Packet0EntityUpdate extends Packet {

	private byte[] rawData;
	private boolean sendEntityData = false;
	private Entity entity;

	public Packet0EntityUpdate(Entity entity) {
		this.entity = entity;
		sendEntityData = true;
	}

	public Packet0EntityUpdate(ByteBuf buffer) {
		int zlibLength = buffer.readInt();
		rawData = new byte[zlibLength];
		buffer.readBytes(rawData);
		try {
			ByteBuf dataBuf = Unpooled.copiedBuffer(ZLibOperations.decompress(this.rawData));
			dataBuf = dataBuf.order(ByteOrder.LITTLE_ENDIAN);
		} catch (Exception exc) {
			ParaGlydar.getLogger().severe(exc, "Exception raised while decoding Packet 0");
		}
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_UPDATE;
	}

	@Override
	public void encode(ByteBuf buf) {
		if (!sendEntityData) {
			buf.writeInt(rawData.length);
			buf.writeBytes(rawData);
		} else {
			ByteBuf buf2 = Unpooled.buffer();
			buf2 = buf2.order(ByteOrder.LITTLE_ENDIAN);
			DataCodec.writeEntityData(buf2, entity.getEntityData());
			byte[] compressedData = null;
			try {
				compressedData = ZLibOperations.compress(buf2.array());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (compressedData != null) {
				buf.writeInt(compressedData.length);
				buf.writeBytes(compressedData);
			} else {
				buf.writeInt(rawData.length);
				buf.writeBytes(rawData);
			}
		}
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		float HP = 1;
		entity = ply;
		if (!ply.joined) {
			//TODO: Temporary, make a proper constant!
			
			ply.setEntityData(new EntityData());
			ply.getEntityData().setEntity(ply);
			try {
				ByteBuf dataBuf = Unpooled.copiedBuffer(ZLibOperations.decompress(this.rawData));
				dataBuf = dataBuf.order(ByteOrder.LITTLE_ENDIAN);
				ply.setEntityData(DataCodec.readEntityData(dataBuf, ply.getEntityData()));
			} catch (Exception exc) {
				ParaGlydar.getLogger().severe(exc, "Exception raised while decoding Packet 0");
			}

			//TODO: Add more functionality to join message!
			String joinMessage = manageJoinEvent(ply);

			Glydar.getServer().getLogger().info(joinMessage);
			for (Entity e : ply.getWorld().getWorldEntities()) {
				if (((GEntity) e).entityID == ply.entityID) {
					continue;
				}
				ply.sendPacket(new Packet0EntityUpdate(e));
			}
			ply.playerJoined();
		} else {
			HP = ply.getEntityData().getHP();
			manageEntityEvents(ply);
			try {
				ByteBuf dataBuf = Unpooled.copiedBuffer(ZLibOperations.decompress(this.rawData));
				dataBuf = dataBuf.order(ByteOrder.LITTLE_ENDIAN);
				ply.setEntityData(DataCodec.readEntityData(dataBuf, ply.getEntityData()));
			} catch (Exception exc) {
				ParaGlydar.getLogger().severe(exc, "Exception raised while decoding Packet 0");
			}
		}

		if (HP <= 0 && ply.getEntityData().getHP() > 0){
			ply.getEntityData().setHostileType((byte) 0);
			//TODO: Insert respawn event here
			ply.forceUpdateData(true);
		}
	}

	public String manageJoinEvent(GPlayer ply) {
		PlayerJoinEvent pje = Glydar.getEventManager().callEvent(new PlayerJoinEvent(ply));
		return pje.getJoinMessage();
	}

	public void manageEntityEvents(GPlayer ply) {
		//TODO: Edit to accomodate new update style
		EntityUpdateEvent event;
		event = Glydar.getEventManager().callEvent(new EntityUpdateEvent(ply, entity.getEntityData()));
		//ed = (GEntityData) event.getEntityData();
	}
}
