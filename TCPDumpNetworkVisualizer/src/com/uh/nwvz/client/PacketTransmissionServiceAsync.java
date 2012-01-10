package com.uh.nwvz.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.shared.dto.NetworkNodeDTO;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;

public interface PacketTransmissionServiceAsync {

	void getPacketInfo(AsyncCallback<PacketInfoDTO> callback);

	void startPacketTransfer(long endDate,
			AsyncCallback<PacketTransferInfoDTO> callback);

	void nextNode(AsyncCallback<NetworkNodeDTO> callback);

}
