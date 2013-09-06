package org.glydar.glydar.protocol.pipeline;

import org.glydar.glydar.protocol.Packet;

/**
 * This class is used to keep track of the packet which leads to the exception
 */
public class ProtocolHandlerException extends Exception {

	private static final long serialVersionUID = -1332842290699958541L;

	private final Packet packet;

	public ProtocolHandlerException(Exception exception, Packet packet) {
		super(exception);
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}
}
