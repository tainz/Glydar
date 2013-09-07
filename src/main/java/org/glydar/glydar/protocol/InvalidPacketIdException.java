package org.glydar.glydar.protocol;

public class InvalidPacketIdException extends RuntimeException {

	private static final long serialVersionUID = 5923823882527347610L;

	public InvalidPacketIdException(int id) {
		super("Invalid packet id " + id +" received (most likely an error in reading a previous packet)");
	}
}
