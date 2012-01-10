package com.uh.nwvz.client.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uh.nwvz.client.components.InformationPopup;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.MouseClickHandler;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.shared.PcapUtil;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class GraphBuilder implements MouseClickHandler {

	private Map<Integer, Association> associations = new HashMap<Integer, Association>();

	private Map<String, NetworkNode> nodes = new HashMap<String, NetworkNode>();

	private NetworkNode homeNode;

	private GfxManager gfxManager;

	private String clientIpAddress;

	public GraphBuilder(GfxManager gfxManager) {
		this.gfxManager = gfxManager;
	}

	private boolean addPacket(SimplePacketDTO packet) {
		Association ass = associations.get(packet.getFlowId());
		String dest = PcapUtil.ip(packet.getDestination());
		String src = PcapUtil.ip(packet.getSource());

		System.out.println(src + " => " + dest + " : " + packet.getFlowId());

		if (ass == null) {
			NetworkNode newNode = null;

			if (!dest.equals(clientIpAddress)) {
				newNode = NetworkNodeFactory.getNetworkNodeFactory()
						.createHTTPNode(dest);
			} else {
				newNode = NetworkNodeFactory.getNetworkNodeFactory()
						.createHTTPNode(src);
			}

			newNode.addMouseClickHandler(this);
			ass = new Association(homeNode, newNode);
			gfxManager.addNode(newNode);
			gfxManager.addAssociation(ass);
			associations.put(packet.getFlowId(), ass);
			nodes.put(newNode.getText(), newNode);
			return true;
		} else {
			NetworkNode node = null;
			if (!dest.equals(clientIpAddress)) {
				node = nodes.get(dest);
			} else {
				node = nodes.get(src);
			}

			if (node != null) {
				node.addPacketReceived();
				return true;
			}

		}

		return false;
	}

	private boolean addNode(com.uh.nwvz.shared.dto.NetworkNodeDTO node) {
		return false;
	}

	public void addNextPacket(SimplePacketDTO packet) {
		if (addPacket(packet))
			gfxManager.forceLayout();
	}

	public void forcePackets(List<SimplePacketDTO> packets) {
		reset();

		for (SimplePacketDTO packet : packets)
			addPacket(packet);
		gfxManager.forceLayout();
	}

	public void forceNodes(List<com.uh.nwvz.shared.dto.NetworkNodeDTO> nodes) {
		reset();

		for (com.uh.nwvz.shared.dto.NetworkNodeDTO node : nodes)
			addNode(node);

		gfxManager.forceLayout();
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
			popup.setData(networkNode.getText(), networkNode.getPacketsSent(),
					networkNode.getPacketsReceived(),
					networkNode.getkByteSent(), networkNode.getkByteReceived());
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
