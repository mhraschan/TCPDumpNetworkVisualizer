package com.uh.nwvz.client.network;

import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.commons.Vector;
import com.uh.nwvz.client.gfx.graph.Node;

public class NetworkNode extends Node {
	
	private int packetsReceived = 0;
	private int packetsSent = 0;
	private long kByteReceived = 0;
	private long kByteSent = 0;

	public NetworkNode(Vector center, double radius, String text, CssColor colorNormal, CssColor colorMouseOver, CssColor colorMouseDown) {
		super(center, radius, text, colorNormal, colorMouseOver, colorMouseDown);
	}
	
	public int getPacketsReceived() {
		return packetsReceived;
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
	
}
