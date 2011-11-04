package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class SimpleTCPPacketDTO implements Serializable {

	private static final long serialVersionUID = -6380895546382176961L;

	private int srcPort;

	private int dstPort;

	private SimpleHttpPacketDTO httpPacket;

	public SimpleTCPPacketDTO() {
		super();
	}

	public SimpleTCPPacketDTO(int srcPort, int dstPort) {
		super();
		this.srcPort = srcPort;
		this.dstPort = dstPort;
	}

	public int getDstPort() {
		return dstPort;
	}

	public SimpleHttpPacketDTO getHttpPacket() {
		return httpPacket;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setDstPort(int dstPort) {
		this.dstPort = dstPort;
	}

	public void setHttpPacket(SimpleHttpPacketDTO httpPacket) {
		this.httpPacket = httpPacket;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

}
