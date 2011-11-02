package com.uh.nwvz.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public interface PacketTransmissionServiceAsync {

	@Deprecated
	void getPackets(AsyncCallback<List<SimplePacketDTO>> callback);

	void getTotalPacketCount(AsyncCallback<Integer> callback);

	void getAllPackets(AsyncCallback<Map<Integer, SimplePacketDTO>> callback);

	void getNextPackets(AsyncCallback<Map<Integer, SimplePacketDTO>> callback);

}
