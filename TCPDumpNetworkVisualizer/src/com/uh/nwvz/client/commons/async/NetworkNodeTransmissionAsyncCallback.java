package com.uh.nwvz.client.commons.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.shared.PcapUtil;
import com.uh.nwvz.shared.dto.NetworkNodeDTO;

public class NetworkNodeTransmissionAsyncCallback implements
		AsyncCallback<NetworkNodeDTO> {
	private PacketReceiveNotifier receiver;

	private LogListener logListener;

	public NetworkNodeTransmissionAsyncCallback(PacketReceiveNotifier receiver,
			LogListener logListener) {
		super();
		this.receiver = receiver;
		this.logListener = logListener;
	}

	@Override
	public void onFailure(Throwable caught) {
		logListener.logError(caught.getMessage());
	}

	@Override
	public void onSuccess(NetworkNodeDTO result) {
		logListener.logInfo("Received node: " + PcapUtil.ip(result.getIp()));

		receiver.receivedNode(result);
	}

}
