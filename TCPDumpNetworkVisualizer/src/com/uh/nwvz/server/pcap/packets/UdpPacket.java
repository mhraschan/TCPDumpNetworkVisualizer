package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.server.pcap.packets.enumerations.UDPHeader;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class UdpPacket implements GeneralPacket {

	private int checksum;

	private int destination;

	private int source;

	private int length;

	private UDPHeader header;

	private GeneralPacket subPacket;

	public UdpPacket(int checksum, int destination, int source, int length) {
		super();
		this.checksum = checksum;
		this.destination = destination;
		this.source = source;
		this.length = length;
	}

	public int getChecksum() {
		return checksum;
	}

	public int getDestination() {
		return destination;
	}

	public UDPHeader getHeader() {
		return header;
	}

	public int getLength() {
		return length;
	}

	public int getSource() {
		return source;
	}

	public GeneralPacket getSubPacket() {
		return subPacket;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public void setHeader(UDPHeader header) {
		this.header = header;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public void setSubPacket(GeneralPacket subPacket) {
		this.subPacket = subPacket;
	}

	@Override
	public boolean hasSubPacket() {
		return subPacket != null;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
		// TODO Auto-generated method stub
		
	}

}
