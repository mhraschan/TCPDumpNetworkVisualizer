package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class PacketTransferInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4280923248414698971L;

	private long packetCount;

	private boolean finished;

	public PacketTransferInfoDTO() {
		super();
	}

	public PacketTransferInfoDTO(long packetCount, boolean finished) {
		super();
		this.packetCount = packetCount;
		this.finished = finished;
	}

	public long getPacketCount() {
		return packetCount;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void setPacketCount(long packetCount) {
		this.packetCount = packetCount;
	}

}
