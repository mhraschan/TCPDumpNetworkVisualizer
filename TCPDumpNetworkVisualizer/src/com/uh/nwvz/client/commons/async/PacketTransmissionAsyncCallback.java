package com.uh.nwvz.client.commons.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketTransmissionAsyncCallback implements
		AsyncCallback<SimplePacketDTO[]> {

	private PacketReceiveNotifier receiver;

	private LogListener logListener;

	public PacketTransmissionAsyncCallback(PacketReceiveNotifier receiver,
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
	public void onSuccess(SimplePacketDTO[] result) {
		logListener.logInfo("Received " + result.length + " packets.");
		receiver.received(result);
	}

}
