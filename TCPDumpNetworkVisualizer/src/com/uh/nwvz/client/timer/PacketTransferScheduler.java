package com.uh.nwvz.client.timer;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.PacketTransmissionServiceAsync;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.components.LogTextArea;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketTransferScheduler {

	AsyncCallback<SimplePacketDTO[]> packetTransmissionCallback = new AsyncCallback<SimplePacketDTO[]>() {
		public void onFailure(Throwable caught) {
			logArea.logError(caught.getMessage());
		}

		public void onSuccess(SimplePacketDTO[] packets) {
			currentPacketCount += packets.length;
			logArea.logInfo(currentPacketCount + " of " + totalPacketCount
					+ " packets received!");
			receiver.received(packets);
//			new PacketTransferScheduler(logArea, totalPacketCount,
//					currentPacketCount, receiver, packetTransmissionSvc)
//					.start();
		}
	};

	private LogTextArea logArea;

	private int totalPacketCount = 0;

	private int currentPacketCount = 0;

	private PacketReceiveNotifier receiver;

	private PacketTransmissionServiceAsync packetTransmissionSvc;

	public PacketTransferScheduler(LogTextArea logArea, int totalPacketCount,
			int currentPacketCount, PacketReceiveNotifier receiver,
			PacketTransmissionServiceAsync packetTransmissionSvc) {
		super();
		this.logArea = logArea;
		this.totalPacketCount = totalPacketCount;
		this.currentPacketCount = currentPacketCount;
		this.receiver = receiver;
		this.packetTransmissionSvc = packetTransmissionSvc;
	}

	public void start() {
		if (currentPacketCount < totalPacketCount) {
			packetTransmissionSvc.getNextPackets(packetTransmissionCallback);
		}
	}

}
