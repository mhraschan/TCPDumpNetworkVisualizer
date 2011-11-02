package com.uh.nwvz.server.pcap.flow;

import java.util.ArrayList;
import java.util.List;

import com.uh.nwvz.server.pcap.packets.Packet;

public class Flow {

	private int id;

	private List<Packet> packets = new ArrayList<Packet>();

	public List<Packet> getPackets() {
		return packets;
	}

	public void setPackets(List<Packet> packets) {
		this.packets = packets;
	}

	public Flow(List<Packet> packets) {
		super();
		this.packets = packets;
	}

	public Flow() {
		super();
	}

	public Flow(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
