package com.uh.nwvz.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.uh.nwvz.shared.SimplePacketType;

public class SimplePacketDTO implements Serializable {

	private static final long serialVersionUID = 8482136288056296984L;

	private Date receiveDate;

	private int packetId;

	private int flowId;

	private SimplePacketType type;

	private long size;

	private byte[] source;

	private byte[] destination;

	private SimpleTCPPacketDTO tcpPacket;

	public byte[] getDestination() {
		return destination;
	}

	public int getFlowId() {
		return flowId;
	}

	public int getPacketId() {
		return packetId;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public long getSize() {
		return size;
	}

	public byte[] getSource() {
		return source;
	}

	public SimpleTCPPacketDTO getTcpPacket() {
		return tcpPacket;
	}

	public SimplePacketType getType() {
		return type;
	}

	public void setDestination(byte[] destination) {
		this.destination = destination;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public void setTcpPacket(SimpleTCPPacketDTO tcpPacket) {
		this.tcpPacket = tcpPacket;
	}

	public void setType(SimplePacketType type) {
		this.type = type;
	}

}
