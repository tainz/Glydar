package org.glydar.glydar.protocol;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.protocol.client.Packet11ChunkDiscovery;
import org.glydar.glydar.protocol.client.Packet12SectorDiscovery;
import org.glydar.glydar.protocol.client.Packet13MissionData;
import org.glydar.glydar.protocol.client.Packet6Interaction;
import org.glydar.glydar.protocol.client.Packet7Hit;
import org.glydar.glydar.protocol.client.Packet8Stealth;
import org.glydar.glydar.protocol.client.Packet9Shoot;
import org.glydar.glydar.protocol.shared.Packet0EntityUpdate;
import org.glydar.glydar.protocol.shared.Packet10Chat;
import org.glydar.glydar.protocol.shared.Packet17VersionExchange;

public enum PacketType {

	ENTITY_UPDATE {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet0EntityUpdate(buf);
		}
	},

	MULTIPLE_ENTITY_UPDATE {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new UnsupportedPacketException(this);
		}
	},

	UPDATE_FINISHED {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new ServerOnlyPacketException(this);
		}
	},

	UNKOWN_3 {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new UnsupportedPacketException(this);
		}
	},

	SERVER_UPDATE {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new ServerOnlyPacketException(this);
		}
	},

	CURRENT_TIME {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new ServerOnlyPacketException(this);
		}
	},

	INTERACTION {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet6Interaction(buf);
		}
	},

	HIT {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet7Hit(buf);
		}
	},

	STEALTH {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet8Stealth(buf);
		}
	},

	SHOOT {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet9Shoot(buf);
		}
	},

	CHAT {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet10Chat(buf);
		}
	},

	CHUNK_DISCOVERY {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet11ChunkDiscovery(buf);
		}
	},

	SECTOR_DISCOVERY {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet12SectorDiscovery(buf);
		}
	},

	MISSION_DATA {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet13MissionData(buf);
		}
	},

	UNKNOWN_14 {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new UnsupportedPacketException(this);
		}
	},

	SEED {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new ServerOnlyPacketException(this);
		}
	},

	JOIN {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new ServerOnlyPacketException(this);
		}
	},

	VERSION_EXCHANGE {

		@Override
		public Packet createPacket(ByteBuf buf) {
			return new Packet17VersionExchange(buf);
		}
	},

	SERVER_FULL {

		@Override
		public Packet createPacket(ByteBuf buf) {
			throw new ServerOnlyPacketException(this);
		}
	};

	public int id() {
		return ordinal();
	}

	public abstract Packet createPacket(ByteBuf buf);

	@Override
	public String toString() {
		return name() + "(" + id() + ")";
	}

	public static PacketType valueOf(int packetId) {
		PacketType[] types = values();
		if (packetId < 0 || packetId >= types.length) {
			throw new InvalidPacketIdException(packetId);
		}

		return types[packetId];
	}
}