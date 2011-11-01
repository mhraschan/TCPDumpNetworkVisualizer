package com.uh.nwvz.shared.dto;

import java.util.Arrays;
import java.util.Date;

public class TCPPacketDTO extends IP4PacketDTO {

	private static final long serialVersionUID = -5480169314236731612L;

	private int dstPort;

	private int srcPort;

	private long ack;

	public TCPPacketDTO() {
		super();
	}

	public TCPPacketDTO(Date receiveDate, byte[] source, byte[] destination,
			int dstPort, int srcPort, long ack) {
		super(receiveDate, source, destination);
		this.dstPort = dstPort;
		this.srcPort = srcPort;
		this.ack = ack;
	}

	public TCPPacketDTO(int dstPort, int srcPort, long ack) {
		super();
		this.dstPort = dstPort;
		this.srcPort = srcPort;
		this.ack = ack;
	}

	public long getAck() {
		return ack;
	}

	public int getDstPort() {
		return dstPort;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setAck(long ack) {
		this.ack = ack;
	}

	@Override
	public String toString() {
		return "TCPPacketDTO [dstPort=" + dstPort + ", srcPort=" + srcPort
				+ ", ack=" + ack + ", getDestination()="
				+ Arrays.toString(getDestination()) + ", getSource()="
				+ Arrays.toString(getSource()) + ", getReceiveDate()="
				+ getReceiveDate() + "]";
	}

	public void setDstPort(int dstPort) {
		this.dstPort = dstPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

}
