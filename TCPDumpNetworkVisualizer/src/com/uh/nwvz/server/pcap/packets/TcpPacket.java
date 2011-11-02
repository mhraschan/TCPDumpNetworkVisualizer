package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.server.pcap.packets.enumerations.TCPHeader;
import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimplePacketDTO;
import com.uh.nwvz.shared.dto.SimpleTCPPacketDTO;

public class TcpPacket implements GeneralPacket {

	private long ack;

	private int destination;

	private int source;

	private int checksum;

	private int flags;

	private int window;

	private TCPHeader header;

	private GeneralPacket subPacket;

	public TcpPacket(long ack, int destination, int source, int checksum,
			int flags, int window) {
		super();
		this.ack = ack;
		this.destination = destination;
		this.source = source;
		this.checksum = checksum;
		this.flags = flags;
		this.window = window;
	}

	public long getAck() {
		return ack;
	}

	public int getChecksum() {
		return checksum;
	}

	public int getDestination() {
		return destination;
	}

	public int getFlags() {
		return flags;
	}

	public TCPHeader getHeader() {
		return header;
	}

	public int getSource() {
		return source;
	}

	public GeneralPacket getSubPacket() {
		return subPacket;
	}

	public int getWindow() {
		return window;
	}

	public void setAck(long ack) {
		this.ack = ack;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public void setHeader(TCPHeader header) {
		this.header = header;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public void setSubPacket(GeneralPacket subPacket) {
		this.subPacket = subPacket;
	}

	public void setWindow(int window) {
		this.window = window;
	}

	@Override
	public boolean hasSubPacket() {
		return subPacket != null;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
		simplePacket.setType(SimplePacketType.TCP);
		SimpleTCPPacketDTO simpleTCPPacket = new SimpleTCPPacketDTO(source, destination);
		simplePacket.setTcpPacket(simpleTCPPacket);
		
		if (hasSubPacket())
			subPacket.fillSimplePacket(simplePacket);
	}

}
