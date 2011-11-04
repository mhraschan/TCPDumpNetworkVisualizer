package com.uh.nwvz.client.commons.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.PacketTransmissionServiceAsync;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.components.LogTextArea;
import com.uh.nwvz.client.timer.PacketTransferScheduler;

public class PacketCountAsyncCallback implements AsyncCallback<Integer> {

	private PacketReceiveNotifier receiver;

	private LogTextArea rtLog;

	private PacketTransmissionServiceAsync packetTransmissionSvc;

	public PacketCountAsyncCallback(PacketReceiveNotifier receiver,
			LogTextArea rtLog,
			PacketTransmissionServiceAsync packetTransmissionSvc) {
		super();
		this.receiver = receiver;
		this.rtLog = rtLog;
		this.packetTransmissionSvc = packetTransmissionSvc;
	}

	public void onFailure(Throwable caught) {
		rtLog.logError(caught.getMessage());
	}

	public void onSuccess(Integer totalPackets) {
		rtLog.logInfo("Total packets: " + totalPackets);
		new PacketTransferScheduler(rtLog, totalPackets, 0, receiver,
				packetTransmissionSvc).start();
	}

}
