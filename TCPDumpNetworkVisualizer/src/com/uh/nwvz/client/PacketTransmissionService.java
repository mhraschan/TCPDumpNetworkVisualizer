package com.uh.nwvz.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

@RemoteServiceRelativePath("packetTransmission")
public interface PacketTransmissionService extends RemoteService {

	@Deprecated
	public List<SimplePacketDTO> getPackets();
	
	public Integer getTotalPacketCount();
	
	public Map<Integer, SimplePacketDTO> getAllPackets();
	
	public Map<Integer, SimplePacketDTO> getNextPackets();
	
}
