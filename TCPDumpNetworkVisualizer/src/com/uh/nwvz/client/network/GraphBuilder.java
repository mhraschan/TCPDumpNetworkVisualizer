package com.uh.nwvz.client.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uh.nwvz.client.components.InformationPopup;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.MouseClickHandler;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.client.gfx.graph.Node;
import com.uh.nwvz.shared.PcapUtil;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class GraphBuilder implements MouseClickHandler {

	private Map<Integer, Association> associations = new HashMap<Integer, Association>();

	private NetworkNode homeNode;

	private GfxManager gfxManager;

	private String clientIpAddress;

	public GraphBuilder(GfxManager gfxManager) {
		this.gfxManager = gfxManager;
	}

	private boolean addPacket(SimplePacketDTO packet) {
		Association ass = associations.get(packet.getFlowId());

		if (ass == null) {
			String dest = PcapUtil.ip(packet.getDestination());
			String src = PcapUtil.ip(packet.getSource());
			Node newNode = null;

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
			return true;
		}

		return false;
		// TODO: Add packet to association
	}

	public void addNextPacket(SimplePacketDTO packet) {
		if (addPacket(packet))
			gfxManager.forceLayout();
	}

	public void forcePackets(List<SimplePacketDTO> packets) {
		reset();
		
		homeNode = NetworkNodeFactory.getNetworkNodeFactory().createHomeNode();
		gfxManager.addNode(homeNode);
		
		for (SimplePacketDTO packet : packets)
			addPacket(packet);
		gfxManager.forceLayout();
	}

	public void reset() {
		gfxManager.reset();
		associations.clear();
		homeNode = NetworkNodeFactory.getNetworkNodeFactory().createHomeNode();
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
