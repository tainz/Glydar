package net.cwserver.models;

public class ServerEntity extends Entity {
	public static final ServerEntity INSTANCE = new ServerEntity();

	private ServerEntity() {
		super(0);
	}
}
