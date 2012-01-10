package com.uh.nwvz.shared.dto;

import java.io.Serializable;

import com.uh.nwvz.shared.SimplePacketType;

public class SimpleIPPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3778797381731039513L;

	private SimplePacketType type;

	private long receiveDate;

	private byte[] srcIpAddr;

	private byte[] destIpAddr;

	private long size;

	private String sourceHostname;

	private String destHostname;

	private SimpleTCPPacket tcpPacket;

	private SimpleUDPPacket udpPacket;

	public SimpleIPPacket() {
		super();
	}

	public SimpleIPPacket(SimplePacketType type, long receiveDate,
			byte[] srcIpAddr, byte[] destIpAddr, long size,
			String sourceHostname, String destHostname) {
		super();
		this.type = type;
		this.receiveDate = receiveDate;
		this.srcIpAddr = srcIpAddr;
		this.destIpAddr = destIpAddr;
		this.size = size;
		this.sourceHostname = sourceHostname;
		this.destHostname = destHostname;
	}

	public String getDestHostname() {
		return destHostname;
	}

	public byte[] getDestIpAddr() {
		return destIpAddr;
	}

	public long getReceiveDate() {
		return receiveDate;
	}

	public long getSize() {
		return size;
	}

	public String getSourceHostname() {
		return sourceHostname;
	}

	public byte[] getSrcIpAddr() {
		return srcIpAddr;
	}

	public SimpleTCPPacket getTcpPacket() {
		return tcpPacket;
	}

	public SimplePacketType getType() {
		return type;
	}

	public SimpleUDPPacket getUdpPacket() {
		return udpPacket;
	}

	public void setDestHostname(String destHostname) {
		this.destHostname = destHostname;
	}

	public void setDestIpAddr(byte[] destIpAddr) {
		this.destIpAddr = destIpAddr;
	}

	public void setReceiveDate(long receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setSourceHostname(String sourceHostname) {
		this.sourceHostname = sourceHostname;
	}

	public void setSrcIpAddr(byte[] srcIpAddr) {
		this.srcIpAddr = srcIpAddr;
	}

	public void setTcpPacket(SimpleTCPPacket tcpPacket) {
		this.tcpPacket = tcpPacket;
	}

	public void setType(SimplePacketType type) {
		this.type = type;
	}

	public void setUdpPacket(SimpleUDPPacket udpPacket) {
		this.udpPacket = udpPacket;
	}

}
