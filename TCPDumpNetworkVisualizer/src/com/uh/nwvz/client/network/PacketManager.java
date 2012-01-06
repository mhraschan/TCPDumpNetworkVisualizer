package com.uh.nwvz.client.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.uh.nwvz.client.PacketTransmissionServiceAsync;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.commons.async.PacketTransferInfoAsyncCallback;
import com.uh.nwvz.client.commons.async.PacketTransmissionAsyncCallback;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketManager implements PacketReceiveNotifier {

	private List<SimplePacketDTO> storedPackets = new ArrayList<SimplePacketDTO>();

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

			storedPackets.clear();

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
		storedPackets.addAll(Arrays.asList(packets));

		if (storedPackets.size() < packetTransferInfo.getPacketCount()) {
			packetTransmissionSvc
					.nextPackets(new PacketTransmissionAsyncCallback(this,
							logListener));
		} else {
			graphBuilder.setClientIpAddress(clientIpAddress);
			graphBuilder.forcePackets(storedPackets);
			transferActive = false;
		}
	}

	@Override
	public void receivedPacketInfo(PacketInfoDTO packetInfo) {
		this.packetInfo = packetInfo;
	}

	@Override
	public void receivedPacketTransferInfo(PacketTransferInfoDTO transferInfo) {
		this.packetTransferInfo = transferInfo;

		if (transferActive) {
			storedPackets.clear();

			packetTransmissionSvc
					.nextPackets(new PacketTransmissionAsyncCallback(this,
							logListener));
		}
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

}
