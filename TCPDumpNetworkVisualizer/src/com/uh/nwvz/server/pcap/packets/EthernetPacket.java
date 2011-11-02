package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.server.pcap.packets.enumerations.EthernetHeader;
import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class EthernetPacket implements GeneralPacket {

	private long checksum;

	private byte[] destination;

	private byte[] source;

	private int type;

	private GeneralPacket subPacket;

	private EthernetHeader header;

	public EthernetPacket(long checksum, byte[] destination, byte[] source,
			int type) {
		super();
		this.checksum = checksum;
		this.destination = destination;
		this.source = source;
		this.type = type;
	}

	public long getChecksum() {
		return checksum;
	}

	public byte[] getDestination() {
		return destination;
	}

	public EthernetHeader getHeader() {
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

	public void setChecksum(long checksum) {
		this.checksum = checksum;
	}

	public void setDestination(byte[] destination) {
		this.destination = destination;
	}

	public void setHeader(EthernetHeader header) {
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

	@Override
	public boolean hasSubPacket() {
		return subPacket != null;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
		simplePacket.setType(SimplePacketType.ETHERNET);
		simplePacket.setSource(getSource());
		simplePacket.setDestination(getDestination());
		
		if (hasSubPacket())
			subPacket.fillSimplePacket(simplePacket);
	}

}
