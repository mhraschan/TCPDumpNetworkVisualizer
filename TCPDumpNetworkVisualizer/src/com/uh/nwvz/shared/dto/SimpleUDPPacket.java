package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class SimpleUDPPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1273645509009565310L;

	private int srcPort;

	private int destPort;

	public SimpleUDPPacket() {
		super();
	}

	public SimpleUDPPacket(int srcPort, int destPort) {
		super();
		this.srcPort = srcPort;
		this.destPort = destPort;
	}

	public int getDestPort() {
		return destPort;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

}
