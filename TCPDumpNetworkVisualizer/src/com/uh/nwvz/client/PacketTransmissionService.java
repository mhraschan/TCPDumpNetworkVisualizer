package com.uh.nwvz.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uh.nwvz.shared.dto.PacketDTO;

@RemoteServiceRelativePath("packetTransmission")
public interface PacketTransmissionService extends RemoteService {

	
	public List<PacketDTO> getPackets();
}
