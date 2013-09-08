package org.glydar.glydar.models;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.protocol.data.GWorldUpdateData;
import org.glydar.glydar.protocol.server.Packet4WorldUpdate;
import org.glydar.glydar.protocol.shared.Packet10Chat;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.models.WorldTarget;

import com.google.common.collect.ImmutableList;

public class GWorld implements World {

	private final long worldId;
	private final String name;
	private final int seed;
	private final Map<Long, Entity> worldEntities = new HashMap<>();
	private Packet4WorldUpdate worldUpdatePacket = new Packet4WorldUpdate();

	private boolean pvp;

	private static long WORLD_ID = 1;

	private static long getNewWorldId() {
		return WORLD_ID++;
	}

	public GWorld(String name, int seed) {
		this.name = name;
		this.seed = seed;
		pvp = false;
		worldId = GWorld.getNewWorldId();
		Glydar.getServer().addWorld(this);
	}

	public long getWorldId() {
		return worldId;
	}

	public int getSeed() {
		return seed;
	}

	public String getName() {
		return name;
	}

	public void addEntity(long entityID, Entity e) {
		if (!worldEntities.containsKey(entityID)) {
			worldEntities.put(entityID, e);
		}
		if (pvp && e.getEntityData().getHostileType() < 3){
			e.getEntityData().setFlags1((byte) 32);
		} else {
			e.getEntityData().setFlags1((byte) 64);
		}
		e.forceUpdateData(true);
	}

	public void removeEntity(long entityID) {
		worldEntities.remove(entityID);
	}

	public List<Player> getWorldPlayers() {
		ImmutableList.Builder<Player> builder = ImmutableList.builder();
		for (Entity entity : worldEntities.values()) {
			if (entity instanceof Player) {
				builder.add((Player) entity);
			}
		}

		return builder.build();
	}

	public List<Entity> getWorldEntities() {
		return ImmutableList.copyOf(worldEntities.values());
	}

	public void broadcastMessage(String message) {
		new Packet10Chat(message, 0).sendTo(new WorldTarget(this));
	}

	@Override
	public boolean isPVPAllowed() {
		return pvp;
	}

	public void setPVPAllowed(boolean allowPVP) {
		if (allowPVP == pvp) {
			return;
		}

		if (allowPVP) {
			for (Entity e : getWorldEntities()){
				e.getEntityData().setFlags1((byte) 32);
				e.forceUpdateData(true);
			}
		} else {
			for (Entity e : getWorldEntities()){
				e.getEntityData().setFlags1((byte) 64);
				e.forceUpdateData(true);
			}
		}
		this.pvp = allowPVP;
	}

	public GWorldUpdateData getServerUpdateData() {
		return worldUpdatePacket.getServerUpdateData();
	}

	public void sendUpdate() {
		if (worldUpdatePacket.getServerUpdateData().hasChanges()) {
			worldUpdatePacket.sendToWorld(this);
			worldUpdatePacket = new Packet4WorldUpdate();
		}
	}
}
