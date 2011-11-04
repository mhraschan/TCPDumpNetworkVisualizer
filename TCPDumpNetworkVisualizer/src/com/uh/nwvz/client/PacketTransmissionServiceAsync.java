package com.uh.nwvz.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public interface PacketTransmissionServiceAsync {

	void getTotalPacketCount(AsyncCallback<Integer> callback);

	void getNextPackets(AsyncCallback<SimplePacketDTO[]> callback);

}
