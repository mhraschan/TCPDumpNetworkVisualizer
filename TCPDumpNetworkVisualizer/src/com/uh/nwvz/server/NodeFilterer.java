package com.uh.nwvz.server;

import java.util.ArrayList;
import java.util.List;

import com.uh.nwvz.shared.dto.NetworkNodeDTO;
import com.uh.nwvz.shared.dto.SimpleIPPacket;

public class NodeFilterer {

	private List<NetworkNodeDTO> nodes;

	private List<NetworkNodeDTO> filteredNodes = new ArrayList<NetworkNodeDTO>();

	private long nodeCount = 0;

	private long packetCount = 0;

	public NodeFilterer(List<NetworkNodeDTO> nodes) {
		super();
		this.nodes = nodes;
	}

	public List<NetworkNodeDTO> getFilteredNodes() {
		return filteredNodes;
	}

	public long getNodeCount() {
		return nodeCount;
	}

	public long getPacketCount() {
		return packetCount;
	}

	public void nodesBefore(long date) {

		for (NetworkNodeDTO node : nodes) {
			if (node.getCreationDate() <= date) {
				nodeCount++;
				NetworkNodeDTO newNode = new NetworkNodeDTO(node.getIp(),
						node.getCreationDate());

				for (SimpleIPPacket packet : node.getReceivedPackets()) {
					if (packet.getReceiveDate() <= date) {
						newNode.addReceivedPacket(packet);
						packetCount++;
					} else
						break;
				}

				for (SimpleIPPacket packet : node.getSentPackets()) {
					if (packet.getReceiveDate() <= date) {
						newNode.addSentPacket(packet);
						packetCount++;
					} else
						break;
				}
			} else
				break;
		}
	}

}
