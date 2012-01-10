package com.uh.nwvz.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uh.nwvz.shared.dto.NetworkNodeDTO;
import com.uh.nwvz.shared.dto.SimpleIPPacket;

public class NodeFilterer {

	private Map<String, NetworkNodeDTO> nodes;

	private List<NetworkNodeDTO> filteredNodes = new ArrayList<NetworkNodeDTO>();

	private int nodeCount = 0;

	private int packetCount = 0;

	public NodeFilterer(Map<String, NetworkNodeDTO> nodes) {
		super();
		this.nodes = nodes;
	}

	public List<NetworkNodeDTO> getFilteredNodes() {
		return filteredNodes;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public int getPacketCount() {
		return packetCount;
	}

	public void nodesBefore(long date) {
		for (NetworkNodeDTO node : nodes.values()) {
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
				
				filteredNodes.add(newNode);
			}
		}
	}

}
