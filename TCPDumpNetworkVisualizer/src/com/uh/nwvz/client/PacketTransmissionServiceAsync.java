package com.uh.nwvz.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.shared.dto.PacketDTO;

public interface PacketTransmissionServiceAsync {

	void getPackets(AsyncCallback<List<PacketDTO>> callback);

}
