package com.uh.nwvz.client.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.client.gfx.graph.Node;
import com.uh.nwvz.shared.PcapUtil;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class GraphBuilder {

	private final static String LOCALHOST = "10.0.0.2";

	private Map<Integer, Association> associations = new HashMap<Integer, Association>();

	private Node homeNode;

	private GfxManager gfxManager;

	public GraphBuilder(GfxManager gfxManager) {
		this.gfxManager = gfxManager;

		homeNode = gfxManager.createMainNode();
		gfxManager.addNode(homeNode);
	}

	private boolean addPacket(SimplePacketDTO packet) {
		Association ass = associations.get(packet.getFlowId());

		if (ass == null) {
			String dest = PcapUtil.ip(packet.getDestination());
			String src = PcapUtil.ip(packet.getSource());
			Node newNode = null;

			if (!dest.equals(LOCALHOST)) {
				newNode = gfxManager.createNode(dest);
			} else {
				newNode = gfxManager.createNode(src);
			}

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
			addNextPacket(packet);
		gfxManager.forceLayout();
	}

	public void reset() {
		gfxManager.reset();
		associations.clear();
		homeNode = gfxManager.createMainNode();
	}

}
