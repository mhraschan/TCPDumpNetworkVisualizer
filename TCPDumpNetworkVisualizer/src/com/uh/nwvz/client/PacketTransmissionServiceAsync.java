package com.uh.nwvz.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public interface PacketTransmissionServiceAsync {

	void getPacketInfo(AsyncCallback<PacketInfoDTO> callback);

	void startPacketTransfer(long endDate,
			AsyncCallback<PacketTransferInfoDTO> callback);

	void nextPackets(AsyncCallback<SimplePacketDTO[]> callback);

}
