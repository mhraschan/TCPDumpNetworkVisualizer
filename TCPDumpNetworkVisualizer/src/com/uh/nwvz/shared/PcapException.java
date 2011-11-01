package com.uh.nwvz.shared;

public class PcapException extends Exception {

	private static final long serialVersionUID = -4175086348169804650L;

	private String message;

	public PcapException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
