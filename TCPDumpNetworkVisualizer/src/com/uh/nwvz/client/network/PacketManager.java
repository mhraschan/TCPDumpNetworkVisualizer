package com.uh.nwvz.client.network;

import java.util.ArrayList;
import java.util.List;

import com.uh.nwvz.client.PacketTransmissionServiceAsync;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.commons.async.NetworkNodeTransmissionAsyncCallback;
import com.uh.nwvz.client.commons.async.PacketTransferInfoAsyncCallback;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketManager implements PacketReceiveNotifier {

	private List<com.uh.nwvz.shared.dto.NetworkNodeDTO> storedNodes = new ArrayList<com.uh.nwvz.shared.dto.NetworkNodeDTO>();

	private PacketInfoDTO packetInfo = null;

	private GraphBuilder graphBuilder;

	private LogListener logListener;

	private PacketTransmissionServiceAsync packetTransmissionSvc;

	private PacketTransferInfoDTO packetTransferInfo;

	private boolean transferActive = false;

	private String clientIpAddress = "";

	public PacketManager(GraphBuilder graphBuilder,
			PacketTransmissionServiceAsync packetTransmissionSvc,
			LogListener logListener) {
		super();
		this.graphBuilder = graphBuilder;
		this.packetTransmissionSvc = packetTransmissionSvc;
		this.logListener = logListener;
	}

	public void setTime(int time) {
		// time is between 0 and 100

		if (transferActive)
			return;

		if (packetInfo != null && time > 1) {
			transferActive = true;
			long totalTime = packetInfo.getLastPacketArrival()
					- packetInfo.getFirstPacketArrival();
			long reducedTime = (long) (totalTime * (((float) time) / 100));
			long packetsBefore = packetInfo.getFirstPacketArrival()
					+ reducedTime;

			storedNodes.clear();

			packetTransmissionSvc.startPacketTransfer(packetsBefore,
					new PacketTransferInfoAsyncCallback(this, logListener));
		}

	}

	public PacketInfoDTO getPacketInfo() {
		return packetInfo;
	}

	public void setPacketInfo(PacketInfoDTO packetInfo) {
		this.packetInfo = packetInfo;
	}

	@Override
	public void received(SimplePacketDTO[] packets) {
		// not implemented
	}

	@Override
	public void receivedPacketInfo(PacketInfoDTO packetInfo) {
		this.packetInfo = packetInfo;
	}

	@Override
	public void receivedPacketTransferInfo(PacketTransferInfoDTO transferInfo) {
		this.packetTransferInfo = transferInfo;

		if (transferActive) {
			storedNodes.clear();

			packetTransmissionSvc
					.nextNode(new NetworkNodeTransmissionAsyncCallback(this,
							logListener));
		}
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	@Override
	public void receivedNode(com.uh.nwvz.shared.dto.NetworkNodeDTO node) {
		storedNodes.add(node);

		if (storedNodes.size() < packetTransferInfo.getNodeCount()) {
			packetTransmissionSvc
					.nextNode(new NetworkNodeTransmissionAsyncCallback(this,
							logListener));
		} else {
			graphBuilder.setClientIpAddress(clientIpAddress);
			graphBuilder.forceNodes(storedNodes);
			transferActive = false;
		}
	}

}
