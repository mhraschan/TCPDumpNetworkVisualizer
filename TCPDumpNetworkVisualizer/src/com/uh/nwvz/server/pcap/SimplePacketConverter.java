package com.uh.nwvz.server.pcap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.uh.nwvz.server.pcap.packets.Packet;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class SimplePacketConverter {

	public final static List<SimplePacketDTO> convert(
			Map<Integer, Packet> packets) {
		
		List<SimplePacketDTO> simplePackets = new ArrayList<SimplePacketDTO>();
		
		Set<Integer> keys = packets.keySet();
		
		for (Integer key : keys) {
			Packet packet = packets.get(key);
			simplePackets.add(packet.convertToSimplePacket());
		}
		
		return simplePackets;
	}

}
