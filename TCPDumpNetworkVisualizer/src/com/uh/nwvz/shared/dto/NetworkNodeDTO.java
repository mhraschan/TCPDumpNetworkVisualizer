package com.uh.nwvz.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NetworkNodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2570287322771213056L;

	private byte[] ip;

	private List<SimpleIPPacket> receivedPackets = new ArrayList<SimpleIPPacket>();

	private List<SimpleIPPacket> sentPackets = new ArrayList<SimpleIPPacket>();

	private long creationDate;

	public NetworkNodeDTO() {
		super();
	}

	public NetworkNodeDTO(byte[] ip, long creationDate) {
		super();
		this.ip = ip;
		this.creationDate = creationDate;
	}

	public void addReceivedPacket(SimpleIPPacket packet) {
		receivedPackets.add(packet);
	}

	public void addSentPacket(SimpleIPPacket packet) {
		sentPackets.add(packet);
	}

	public long getCreationDate() {
		return creationDate;
	}

	public byte[] getIp() {
		return ip;
	}

	public List<SimpleIPPacket> getReceivedPackets() {
		return receivedPackets;
	}

	public List<SimpleIPPacket> getSentPackets() {
		return sentPackets;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public void setIp(byte[] ip) {
		this.ip = ip;
	}

	public void setReceivedPackets(List<SimpleIPPacket> receivedPackets) {
		this.receivedPackets = receivedPackets;
	}

	public void setSentPackets(List<SimpleIPPacket> sentPackets) {
		this.sentPackets = sentPackets;
	}

}
