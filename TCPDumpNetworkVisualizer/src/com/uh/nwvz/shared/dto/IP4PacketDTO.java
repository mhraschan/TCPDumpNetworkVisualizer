package com.uh.nwvz.shared.dto;

import java.util.Arrays;
import java.util.Date;

public class IP4PacketDTO extends PacketDTO {

	private static final long serialVersionUID = 7927668649481207404L;

	private byte[] source;

	private byte[] destination;

	public IP4PacketDTO() {
		super();
	}

	public IP4PacketDTO(Date receiveDate, byte[] source, byte[] destination) {
		super(receiveDate);
		this.source = source;
		this.destination = destination;
	}

	public IP4PacketDTO(byte[] source, byte[] destination) {
		super();
		this.source = source;
		this.destination = destination;
	}

	public byte[] getDestination() {
		return destination;
	}

	public byte[] getSource() {
		return source;
	}

	public void setDestination(byte[] destination) {
		this.destination = destination;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "IP4PacketDTO [source=" + Arrays.toString(source)
				+ ", destination=" + Arrays.toString(destination)
				+ ", getReceiveDate()=" + getReceiveDate() + "]";
	}

}
