package com.uh.nwvz.client.network;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uh.nwvz.client.components.InformationPopup;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.MouseClickHandler;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.shared.PcapUtil;
import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimpleIPPacket;

public class GraphBuilder implements MouseClickHandler {

	private Map<Integer, Association> associations = new HashMap<Integer, Association>();

	private Map<String, NetworkNode> nodes = new HashMap<String, NetworkNode>();

	private NetworkNode homeNode;

	private GfxManager gfxManager;

	private String clientIpAddress;
	
	private long sizeSum = 0;

	public GraphBuilder(GfxManager gfxManager) {
		this.gfxManager = gfxManager;
	}
	
	private Protocol convertPacketTypeToProtocol(SimplePacketType type) {
		switch (type) {
		case UNKOWN:
			return Protocol.OTHER;
		case HTTP:
			return Protocol.HTTP;
		case TCP:
			return Protocol.TCP;
		case ARP:
			return Protocol.OTHER;
		case UDP:
			return Protocol.UDP;
		case ICMP: 
			return Protocol.ICMP;
		}
		return Protocol.OTHER;
	}
	
	private boolean addNode(com.uh.nwvz.shared.dto.NetworkNodeDTO node)  {
		NetworkNodeFactory factory = NetworkNodeFactory.getNetworkNodeFactory();
		
		String nodeName = PcapUtil.ip(node.getIp());
		String hostname = (node.getReceivedPackets().size() > 0) ? node
				.getReceivedPackets().get(0).getDestHostname() : "";
		
		EnumSet<Protocol> protocols = EnumSet.noneOf(Protocol.class);
		
		long receivedBytes = 0;
		long sentBytes = 0;
		for (SimpleIPPacket packet : node.getReceivedPackets()) {
			protocols.add(convertPacketTypeToProtocol(packet.getType()));
			receivedBytes += packet.getSize();
		}
		for (SimpleIPPacket packet : node.getSentPackets()) {
			protocols.add(convertPacketTypeToProtocol(packet.getType()));
			sentBytes += packet.getSize();
		}

		
		NetworkNode netNode = null;
		if (nodeName.equals(clientIpAddress)) {
			netNode = homeNode;
		} else {
			sizeSum += sentBytes;
			sizeSum += receivedBytes;
			if (protocols.size() == 1) {
				switch(protocols.iterator().next()) 
				{
				case HTTP:
					netNode = factory.createHTTPNode(nodeName, hostname);
					break;
				case UDP:
					netNode = factory.createUDPNode(nodeName, hostname);
					break;
				case TCP:
					netNode = factory.createTCPNode(nodeName, hostname);
					break;
				case ICMP:
					netNode = factory.createICMPNode(nodeName, hostname);
					break;
				case OTHER:
				default:
					netNode = factory.createOtherNode(nodeName, hostname);
				}
			} else {
				netNode = factory.createMixedNode(nodeName, hostname);
			}
			
			Association assoc = new Association(homeNode, netNode);
			associations.put(assoc.hashCode(), assoc);

			gfxManager.addNode(netNode);		
			gfxManager.addAssociation(assoc);
		}
				
		netNode.setProtocols(protocols);
		netNode.setPacketsReceived(node.getReceivedPackets().size());
		netNode.setPacketsSent(node.getSentPackets().size());
		netNode.setkByteReceived((int)(receivedBytes/1024));
		netNode.setkByteSent((int)(sentBytes/1024));
		netNode.setNodeData(node);	
		netNode.addMouseClickHandler(this);
		
		nodes.put(nodeName, netNode);
		
		return true;
	}

	public void forceNodes(List<com.uh.nwvz.shared.dto.NetworkNodeDTO> nodes) {
		reset();

		NetworkNodeFactory.getNetworkNodeFactory().setNodeCount(nodes.size()-1);
		
		sizeSum = 0;
		for (com.uh.nwvz.shared.dto.NetworkNodeDTO node : nodes)
			addNode(node);
		
		float avg = sizeSum / nodes.size();
		for (NetworkNode node : this.nodes.values()) {
			if (node == homeNode)
				continue;
			
			long sum = (node.getkByteSent() + node.getkByteReceived())*1024;
			if (sum <= 0.5 * avg)
				node.setRadius(node.getRadius()*0.65);
			else if (sum < 0.8 * avg)
				node.setRadius(node.getRadius()*0.8);
			else if (sum < 1.5 * avg)
				node.setRadius(node.getRadius());
			else if (sum < 2 * avg)
				node.setRadius(node.getRadius()*1.25);
			else
				node.setRadius(node.getRadius()*1.5);
		}

		//gfxManager.forceLayout();
	}

	private void reset() {
		gfxManager.reset();
		associations.clear();
		nodes.clear();
		homeNode = NetworkNodeFactory.getNetworkNodeFactory().createHomeNode();
		gfxManager.addNode(homeNode);
	}

	@Override
	public void onMouseClick(IGfxObject object) {
		if (object instanceof NetworkNode) {
			NetworkNode networkNode = (NetworkNode) object;
			InformationPopup popup = new InformationPopup();
			String protocolString = "";
			for (Protocol p : networkNode.getProtocols()) {
				protocolString += p.toString() + " ";
			}
			popup.setData(networkNode.getText(), networkNode.getHostname(),
					networkNode.getPacketsSent(),
					networkNode.getPacketsReceived(),
					networkNode.getkByteSent(), networkNode.getkByteReceived(),
					protocolString);
			popup.show();
		}
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

}
