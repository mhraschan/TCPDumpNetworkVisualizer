package com.uh.nwvz.server.pcap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

import com.uh.nwvz.shared.dto.FlowDTO;
import com.uh.nwvz.shared.dto.HttpPacketDTO;
import com.uh.nwvz.shared.dto.IP4PacketDTO;
import com.uh.nwvz.shared.dto.PacketDTO;
import com.uh.nwvz.shared.dto.TCPPacketDTO;

public class PacketHandler implements PcapPacketHandler<StringBuilder> {

	private final Integer flowKey = 1;

	private final Ethernet ethernet = new Ethernet();

	private final Ip4 ip4 = new Ip4();

	private final Tcp tcp = new Tcp();

	private final Http http = new Http();

	private Map<Integer, FlowDTO> flows = new HashMap<Integer, FlowDTO>();

	private List<PacketDTO> packets = new ArrayList<PacketDTO>();
	
	private long packetCount = 0;

	@Override
	public void nextPacket(PcapPacket packet, StringBuilder errbuf) {

		packetCount++;
		
		Date receiveDate = new Date(packet.getCaptureHeader()
				.timestampInMillis());

		if (packet.hasHeader(Http.ID)) {
			packet.getHeader(http);
			packet.getHeader(tcp);
			packet.getHeader(ip4);

			HttpPacketDTO httpDto = new HttpPacketDTO(receiveDate,
					ip4.source(), ip4.destination(), tcp.destination(),
					tcp.source(), tcp.ack(),
					http.fieldValue(Http.Request.Referer));

			packets.add(httpDto);
		} else if (packet.hasHeader(Tcp.ID)) {
			packet.getHeader(tcp);
			packet.getHeader(ip4);

			TCPPacketDTO tcpDto = new TCPPacketDTO(receiveDate, ip4.source(),
					ip4.destination(), tcp.destination(), tcp.source(),
					tcp.ack());

			packets.add(tcpDto);
		} else if (packet.hasHeader(Ip4.ID)) {
			packet.getHeader(ip4);

			IP4PacketDTO ip4Dto = new IP4PacketDTO(receiveDate, ip4.source(),
					ip4.destination());

			packets.add(ip4Dto);
		} else if (packet.hasHeader(Ethernet.ID)) {

		} else {
			PacketDTO packetDto = new PacketDTO(receiveDate);
			packets.add(packetDto);
		}

	}

	public List<PacketDTO> getPackets() {
		return packets;
	}

	public long getPacketCount() {
		return packetCount;
	}

}
