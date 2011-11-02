package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class RipPacket implements GeneralPacket {

	private int routingTableLength;

	public RipPacket(int routingTableLength) {
		super();
		this.routingTableLength = routingTableLength;
	}

	public int getRoutingTableLength() {
		return routingTableLength;
	}

	public void setRoutingTableLength(int routingTableLength) {
		this.routingTableLength = routingTableLength;
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
