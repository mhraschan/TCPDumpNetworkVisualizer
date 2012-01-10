package com.uh.nwvz.shared.dto;

import java.io.Serializable;

public class SimpleHttpPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649457488255030403L;

	private String referer;

	private String contentType;

	public SimpleHttpPacket() {
		super();
	}

	public SimpleHttpPacket(String referer, String contentType) {
		super();
		this.referer = referer;
		this.contentType = contentType;
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

}
