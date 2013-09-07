package org.glydar.glydar.protocol;

public class ServerOnlyPacketException extends RuntimeException {

	private static final long serialVersionUID = 6654591783618203766L;

	public ServerOnlyPacketException(PacketType type) {
		super("Packet " + type + " is server only");
	}
}
