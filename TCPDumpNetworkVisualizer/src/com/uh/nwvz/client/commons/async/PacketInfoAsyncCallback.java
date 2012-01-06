package com.uh.nwvz.client.commons.async;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.shared.dto.PacketInfoDTO;

public class PacketInfoAsyncCallback implements AsyncCallback<PacketInfoDTO> {

	private PacketReceiveNotifier receiver;

	private LogListener logListener;

	public PacketInfoAsyncCallback(PacketReceiveNotifier receiver,
			LogListener logListener) {
		super();
		this.receiver = receiver;
		this.logListener = logListener;
	}

	public void onFailure(Throwable caught) {
		logListener.logError(caught.getMessage());
	}

	@Override
	public void onSuccess(PacketInfoDTO result) {
		logListener.logInfo("Total packets: " + result.getTotalPacketCount());
		logListener.logInfo("First packet: "
				+ new Date(result.getFirstPacketArrival()));
		logListener.logInfo("Last packet: "
				+ new Date(result.getLastPacketArrival()));

		receiver.receivedPacketInfo(result);
	}

}
