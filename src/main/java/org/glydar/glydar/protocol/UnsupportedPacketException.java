package org.glydar.glydar.protocol;

public class UnsupportedPacketException extends RuntimeException {

	private static final long serialVersionUID = -2035216288286599511L;

	public UnsupportedPacketException(PacketType type) {
		super("Packet " + type + " is not supported");
	}
}
