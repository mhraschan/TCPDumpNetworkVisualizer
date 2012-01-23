package com.uh.nwvz.client.network;

import java.util.EnumSet;

import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.commons.Vector;
import com.uh.nwvz.client.gfx.graph.Node;
import com.uh.nwvz.shared.dto.NetworkNodeDTO;

public class NetworkNode extends Node {

	private int packetsReceived = 0;
	private int packetsSent = 0;
	private long kByteReceived = 0;
	private long kByteSent = 0;
	private EnumSet<Protocol> protocols = EnumSet.noneOf(Protocol.class);
	private String hostname;

	private NetworkNodeDTO nodeData = null;

	public NetworkNode(Vector center, double radius, String text, String hostname,
			CssColor colorNormal, CssColor colorMouseOver,
			CssColor colorMouseDown) {
		super(center, radius, text, colorNormal, colorMouseOver, colorMouseDown);
		this.hostname = hostname;
	}

	public EnumSet<Protocol> getProtocols() {
		return protocols;
	}

	public void setProtocols(EnumSet<Protocol> protocols) {
		this.protocols = protocols;
	}

	public int getPacketsReceived() {
		return packetsReceived;
	}

	public NetworkNodeDTO getNodeData() {
		return nodeData;
	}

	public void setNodeData(NetworkNodeDTO nodeData) {
		this.nodeData = nodeData;
	}

	public void setPacketsReceived(int packetsReceived) {
		this.packetsReceived = packetsReceived;
	}

	public int getPacketsSent() {
		return packetsSent;
	}

	public void setPacketsSent(int packetsSent) {
		this.packetsSent = packetsSent;
	}

	public long getkByteReceived() {
		return kByteReceived;
	}

	public void setkByteReceived(int kByteReceived) {
		this.kByteReceived = kByteReceived;
	}

	public long getkByteSent() {
		return kByteSent;
	}

	public void setkByteSent(int kByteSent) {
		this.kByteSent = kByteSent;
	}

	public void addPacketReceived() {
		this.packetsSent++;
	}

	public void addkByteReceived(int kByteReceived) {
		this.kByteReceived += kByteReceived;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
