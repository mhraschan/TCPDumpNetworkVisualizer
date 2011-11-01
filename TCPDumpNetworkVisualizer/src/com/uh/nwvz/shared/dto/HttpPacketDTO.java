package com.uh.nwvz.shared.dto;

import java.util.Arrays;
import java.util.Date;

public class HttpPacketDTO extends TCPPacketDTO {

	private static final long serialVersionUID = -9003329578359504478L;

	private String referer;

	public HttpPacketDTO() {
		super();
	}

	public HttpPacketDTO(Date receiveDate, byte[] source, byte[] destination,
			int dstPort, int srcPort, long ack, String referer) {
		super(receiveDate, source, destination, dstPort, srcPort, ack);
		this.referer = referer;
	}

	public HttpPacketDTO(String referer) {
		super();
		this.referer = referer;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	@Override
	public String toString() {
		return "HttpPacketDTO [referer=" + referer + ", getAck()=" + getAck()
				+ ", getDstPort()=" + getDstPort() + ", getSrcPort()="
				+ getSrcPort() + ", getDestination()="
				+ Arrays.toString(getDestination()) + ", getSource()="
				+ Arrays.toString(getSource()) + ", getReceiveDate()="
				+ getReceiveDate() + "]";
	}

}
