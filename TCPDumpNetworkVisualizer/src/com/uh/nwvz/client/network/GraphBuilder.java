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

	private final static String LOCALHOST = "10.0.2.15";

	private Map<Integer, Association> associations = new HashMap<Integer, Association>();

	private NetworkNode homeNode;

	private GfxManager gfxManager;

	public GraphBuilder(GfxManager gfxManager) {
		this.gfxManager = gfxManager;

		homeNode = NetworkNodeFactory.getNetworkNodeFactory().createHomeNode();
		gfxManager.addNode(homeNode);
		homeNode.addMouseClickHandler(this);
	}

	private boolean addPacket(SimplePacketDTO packet) {
		Association ass = associations.get(packet.getFlowId());

		if (ass == null) {
			String dest = PcapUtil.ip(packet.getDestination());
			String src = PcapUtil.ip(packet.getSource());
			Node newNode = null;

			if (!dest.equals(LOCALHOST)) {
				newNode = NetworkNodeFactory.getNetworkNodeFactory().createHTTPNode(dest);
			} else {
				newNode = NetworkNodeFactory.getNetworkNodeFactory().createHTTPNode(src);
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
					networkNode.getPacketsReceived(), networkNode.getkByteSent(),
					networkNode.getkByteReceived());
			popup.show();	
		}
	}

}
