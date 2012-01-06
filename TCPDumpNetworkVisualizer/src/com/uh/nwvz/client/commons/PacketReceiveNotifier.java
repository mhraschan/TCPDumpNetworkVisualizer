package com.uh.nwvz.client.commons;

import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public interface PacketReceiveNotifier {

	public void received(SimplePacketDTO[] packets);

	public void receivedPacketInfo(PacketInfoDTO packetInfo);

	public void receivedPacketTransferInfo(PacketTransferInfoDTO transferInfo);
}
