package com.uh.nwvz.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.uh.nwvz.shared.dto.NetworkNodeDTO;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;

@RemoteServiceRelativePath("packetTransmission")
public interface PacketTransmissionService extends RemoteService {

	public NetworkNodeDTO nextNode();

	public PacketTransferInfoDTO startPacketTransfer(long endDate);

	public PacketInfoDTO getPacketInfo();

}
