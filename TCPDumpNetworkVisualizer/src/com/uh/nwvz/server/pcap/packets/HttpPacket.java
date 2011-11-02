package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimpleHttpPacketDTO;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class HttpPacket implements GeneralPacket {

	private String contentType;

	private String referer;

	public HttpPacket(String contentType, String referer) {
		super();
		this.contentType = contentType;
		this.referer = referer;
	}

	public String getContentType() {
		return contentType;
	}

	public String getReferer() {
		return referer;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	@Override
	public boolean hasSubPacket() {
		return false;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
		simplePacket.setType(SimplePacketType.HTTP);
		SimpleHttpPacketDTO simpleHttpPacket = new SimpleHttpPacketDTO(getReferer());
		simplePacket.getTcpPacket().setHttpPacket(simpleHttpPacket);
	}

}
