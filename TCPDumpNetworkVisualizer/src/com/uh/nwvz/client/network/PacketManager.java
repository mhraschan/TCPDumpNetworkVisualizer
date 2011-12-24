package com.uh.nwvz.client.network;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketManager extends Timer {

	private final static int START_DELAY = 500;

	private List<SimplePacketDTO> packets = new ArrayList<SimplePacketDTO>();

	private int totalPacketCount = 0;

	private int currentPacketCount = 0;

	private int currentPacketKey = 0;

	private boolean running = false;

	private boolean initialized = false;

	private GraphBuilder graphBuilder;

	private LogListener logListener; 

	public PacketManager(GraphBuilder graphBuilder, LogListener logListener) {
		super();
		this.graphBuilder = graphBuilder;
		this.logListener = logListener;
	}
	
	public void setTime(int time) {
		// load data
		// build graph
	}

	public void addPackets(SimplePacketDTO[] newPackets) {
		for (SimplePacketDTO newPacket : newPackets)
			packets.add(newPacket);

		currentPacketCount += newPackets.length;
	}

	public boolean start() {
		if (currentPacketCount > 0) {
			if (!initialized) {
				currentPacketKey = 0;
				this.scheduleRepeating(START_DELAY);
				initialized = true;
			}

			running = true;
			return true;
		}

		return false;
	}

	public void stop() {
		running = false;
	}

	public void setCurrentPacket(int currentPacket) {
		if (currentPacket <= currentPacketCount)
			this.currentPacketKey = currentPacket;
	}

	public int getCurrentPacketCount() {
		return currentPacketCount;
	}

	public int getCurrentPacketKey() {
		return currentPacketKey;
	}
	
	public void forceDrawPackets(int lastPacketKey) {
		boolean wasRunning = running;
		running = false;
		
		List<SimplePacketDTO> sublist = packets.subList(0, lastPacketKey);
		graphBuilder.forcePackets(sublist);
		
		currentPacketKey = lastPacketKey;
		running = wasRunning;
	}
	
	@Override
	public void run() {
		if (running && currentPacketCount > 0 && currentPacketKey < currentPacketCount) {
			SimplePacketDTO currentPacket = packets.get(currentPacketKey);

			logListener.logInfo("Processing packet " + currentPacket.getPacketId());
			graphBuilder.addNextPacket(currentPacket);
			
			currentPacketKey++;
		}
	}
}
