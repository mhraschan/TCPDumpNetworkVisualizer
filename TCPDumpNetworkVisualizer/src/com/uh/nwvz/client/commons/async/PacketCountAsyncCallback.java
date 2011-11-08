package com.uh.nwvz.client.commons.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.PacketTransmissionServiceAsync;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.timer.PacketTransferScheduler;

public class PacketCountAsyncCallback implements AsyncCallback<Integer> {

	private PacketReceiveNotifier receiver;

	private LogListener logListener;

	private PacketTransmissionServiceAsync packetTransmissionSvc;

	public PacketCountAsyncCallback(PacketReceiveNotifier receiver,
			LogListener logListener,
			PacketTransmissionServiceAsync packetTransmissionSvc) {
		super();
		this.receiver = receiver;
		this.logListener = logListener;
		this.packetTransmissionSvc = packetTransmissionSvc;
	}

	public void onFailure(Throwable caught) {
		logListener.logError(caught.getMessage());
	}

	public void onSuccess(Integer totalPackets) {
		logListener.logInfo("Total packets: " + totalPackets);
		new PacketTransferScheduler(logListener, totalPackets, 0, receiver,
				packetTransmissionSvc).start();
	}

}
