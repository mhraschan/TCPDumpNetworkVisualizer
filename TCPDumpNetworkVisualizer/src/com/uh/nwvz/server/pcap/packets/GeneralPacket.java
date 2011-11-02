package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.shared.dto.SimplePacketDTO;

public interface GeneralPacket {

	public boolean hasSubPacket();
	
	public void fillSimplePacket(SimplePacketDTO simplePacket);
	
}
