package com.uh.nwvz.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

@RemoteServiceRelativePath("packetTransmission")
public interface PacketTransmissionService extends RemoteService {

	public Integer getTotalPacketCount();
	
	public SimplePacketDTO[] getNextPackets();
	
}
