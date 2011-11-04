package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.server.pcap.packets.enumerations.IPHeader;
import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class IP6Packet implements GeneralPacket {

	private byte[] source;

	private byte[] destination;

	private int version;

	private IPHeader header;

	private GeneralPacket subPacket;

	public IP6Packet(byte[] source, byte[] destination, int version) {
		super();
		this.source = source;
		this.destination = destination;
		this.version = version;
	}

	public byte[] getDestination() {
		return destination;
	}

	public IPHeader getHeader() {
		return header;
	}

	public byte[] getSource() {
		return source;
	}

	public GeneralPacket getSubPacket() {
		return subPacket;
	}

	public int getVersion() {
		return version;
	}

	public void setDestination(byte[] destination) {
		this.destination = destination;
	}

	public void setHeader(IPHeader header) {
		this.header = header;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public void setSubPacket(GeneralPacket subPacket) {
		this.subPacket = subPacket;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean hasSubPacket() {
		return subPacket != null;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
		simplePacket.setType(SimplePacketType.IP6);
		simplePacket.setDestination(destination);
		simplePacket.setSource(source);
		
		if (hasSubPacket())
			subPacket.fillSimplePacket(simplePacket);
	}

}
