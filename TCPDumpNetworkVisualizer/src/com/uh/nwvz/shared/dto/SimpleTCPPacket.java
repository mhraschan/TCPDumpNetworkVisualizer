package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class SimpleTCPPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7363362596041994990L;

	private int srcPort;

	private int destPort;

	private SimpleHttpPacket httpPacket;

	public SimpleTCPPacket() {
		super();
	}

	public SimpleTCPPacket(int srcPort, int destPort) {
		super();
		this.srcPort = srcPort;
		this.destPort = destPort;
	}

	public int getDestPort() {
		return destPort;
	}

	public SimpleHttpPacket getHttpPacket() {
		return httpPacket;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public void setHttpPacket(SimpleHttpPacket httpPacket) {
		this.httpPacket = httpPacket;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

}
