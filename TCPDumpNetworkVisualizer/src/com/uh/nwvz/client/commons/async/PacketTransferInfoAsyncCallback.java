package com.uh.nwvz.client.commons.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;

public class PacketTransferInfoAsyncCallback implements
		AsyncCallback<PacketTransferInfoDTO> {

	private PacketReceiveNotifier receiver;

	private LogListener logListener;

	public PacketTransferInfoAsyncCallback(PacketReceiveNotifier receiver,
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
	public void onSuccess(PacketTransferInfoDTO result) {
		logListener.logInfo("Packets to transfer: " + result.getPacketCount());

		receiver.receivedPacketTransferInfo(result);
	}

}
