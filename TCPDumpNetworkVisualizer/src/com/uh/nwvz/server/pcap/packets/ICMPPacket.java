package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class ICMPPacket implements GeneralPacket {

	private int checksum;

	private int code;

	private int type;

	public ICMPPacket(int checksum, int code, int type) {
		super();
		this.checksum = checksum;
		this.code = code;
		this.type = type;
	}

	public int getChecksum() {
		return checksum;
	}

	public int getCode() {
		return code;
	}

	public int getType() {
		return type;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public boolean hasSubPacket() {
		return false;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
		// TODO Auto-generated method stub
		
	}

}
