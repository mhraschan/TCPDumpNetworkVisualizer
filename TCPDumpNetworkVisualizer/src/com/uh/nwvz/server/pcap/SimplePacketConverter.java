package com.uh.nwvz.server.pcap;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.uh.nwvz.server.pcap.packets.Packet;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class SimplePacketConverter {

	public final static Map<Integer, SimplePacketDTO> convert(
			Map<Integer, Packet> packets) {
		
		Map<Integer, SimplePacketDTO> simplePackets = new TreeMap<Integer, SimplePacketDTO>();
		
		Set<Integer> keys = packets.keySet();
		
		for (Integer key : keys) {
			Packet packet = packets.get(key);
			simplePackets.put(key, packet.convertToSimplePacket());
		}
		
		return simplePackets;
	}

}
