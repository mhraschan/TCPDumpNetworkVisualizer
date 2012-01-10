package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class PacketInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 850852852940900258L;

	private Long firstPacketArrival;

	private Long lastPacketArrival;

	private Long totalPacketCount;

	private Long tcpPacketCount;

	private Long nodeCount;

	public PacketInfoDTO() {
		super();
	}

	public Long getTcpPacketCount() {
		return tcpPacketCount;
	}

	public void setTcpPacketCount(Long tcpPacketCount) {
		this.tcpPacketCount = tcpPacketCount;
	}

	public Long getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(Long nodeCount) {
		this.nodeCount = nodeCount;
	}

	public PacketInfoDTO(Long firstPacketArrival, Long lastPacketArrival,
			Long totalPacketCount, Long tcpPacketCount, Long nodeCount) {
		super();
		this.firstPacketArrival = firstPacketArrival;
		this.lastPacketArrival = lastPacketArrival;
		this.totalPacketCount = totalPacketCount;
		this.tcpPacketCount = tcpPacketCount;
		this.nodeCount = nodeCount;
	}

	public Long getFirstPacketArrival() {
		return firstPacketArrival;
	}

	public Long getLastPacketArrival() {
		return lastPacketArrival;
	}

	public Long getTotalPacketCount() {
		return totalPacketCount;
	}

	public void setFirstPacketArrival(Long firstPacketArrival) {
		this.firstPacketArrival = firstPacketArrival;
	}

	public void setLastPacketArrival(Long lastPacketArrival) {
		this.lastPacketArrival = lastPacketArrival;
	}

	public void setTotalPacketCount(Long totalPacketCount) {
		this.totalPacketCount = totalPacketCount;
	}

}
