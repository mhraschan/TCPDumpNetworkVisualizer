package com.uh.nwvz.client.timer;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.uh.nwvz.client.PacketTransmissionServiceAsync;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketTransferScheduler {

	AsyncCallback<SimplePacketDTO[]> packetTransmissionCallback = new AsyncCallback<SimplePacketDTO[]>() {
		public void onFailure(Throwable caught) {
			logListener.logError(caught.getMessage());
		}

		public void onSuccess(SimplePacketDTO[] packets) {
			currentPacketCount += packets.length;
			logListener.logInfo(currentPacketCount + " of " + totalPacketCount
					+ " packets received!");
			receiver.received(packets);
			//TODO: Uncomment to receive more packets
//			new PacketTransferScheduler(logArea, totalPacketCount,
//					currentPacketCount, receiver, packetTransmissionSvc)
//					.start();
		}
	};

	private LogListener logListener;

	private int totalPacketCount = 0;

	private int currentPacketCount = 0;

	private PacketReceiveNotifier receiver;

	private PacketTransmissionServiceAsync packetTransmissionSvc;

	public PacketTransferScheduler(LogListener logListener, int totalPacketCount,
			int currentPacketCount, PacketReceiveNotifier receiver,
			PacketTransmissionServiceAsync packetTransmissionSvc) {
		super();
		this.logListener = logListener;
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
