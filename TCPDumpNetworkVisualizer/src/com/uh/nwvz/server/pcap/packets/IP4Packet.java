package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.server.pcap.packets.enumerations.IPHeader;
import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class IP4Packet implements GeneralPacket {

	private int checksum;

	private byte[] destination;

	private byte[] source;

	private String destinationHostName;

	private String sourceHostName;

	private int flags;

	private int version;

	private int type;

	private IPHeader header;

	private GeneralPacket subPacket;

	public IP4Packet(int checksum, byte[] destination, byte[] source,
			String destinationHostName, String sourceHostName, int flags,
			int version, int type) {
		super();
		this.checksum = checksum;
		this.destination = destination;
		this.source = source;
		this.destinationHostName = destinationHostName;
		this.sourceHostName = sourceHostName;
		this.flags = flags;
		this.version = version;
		this.type = type;
	}

	public int getChecksum() {
		return checksum;
	}

	public byte[] getDestination() {
		return destination;
	}

	public int getFlags() {
		return flags;
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

	public int getType() {
		return type;
	}

	public int getVersion() {
		return version;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	public void setDestination(byte[] destination) {
		this.destination = destination;
	}

	public void setFlags(int flags) {
		this.flags = flags;
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

	public void setType(int type) {
		this.type = type;
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
		simplePacket.setType(SimplePacketType.IP4);
		simplePacket.setDestination(destination);
		simplePacket.setSource(source);
		simplePacket.setDestHostname(destinationHostName);
		simplePacket.setSourceHostname(sourceHostName);

		if (hasSubPacket())
			subPacket.fillSimplePacket(simplePacket);
	}

	public String getDestinationHostName() {
		return destinationHostName;
	}

	public void setDestinationHostName(String destinationHostName) {
		this.destinationHostName = destinationHostName;
	}

	public String getSourceHostName() {
		return sourceHostName;
	}

	public void setSourceHostName(String sourceHostName) {
		this.sourceHostName = sourceHostName;
	}

}
