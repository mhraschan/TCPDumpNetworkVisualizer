package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class SimpleHttpPacketDTO implements Serializable {

	private static final long serialVersionUID = 8508541295240900258L;

	private String referer;

	public SimpleHttpPacketDTO() {
		super();
	}

	public SimpleHttpPacketDTO(String referer) {
		super();
		this.referer = referer;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

}
