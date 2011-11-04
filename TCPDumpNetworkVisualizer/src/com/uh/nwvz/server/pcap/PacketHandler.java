package com.uh.nwvz.server.pcap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jnetpcap.packet.JFlowKey;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import com.uh.nwvz.server.pcap.flow.Flow;
import com.uh.nwvz.server.pcap.packets.EthernetPacket;
import com.uh.nwvz.server.pcap.packets.HttpPacket;
import com.uh.nwvz.server.pcap.packets.ICMPPacket;
import com.uh.nwvz.server.pcap.packets.IP4Packet;
import com.uh.nwvz.server.pcap.packets.IP6Packet;
import com.uh.nwvz.server.pcap.packets.Packet;
import com.uh.nwvz.server.pcap.packets.TcpPacket;
import com.uh.nwvz.server.pcap.packets.UdpPacket;
import com.uh.nwvz.server.pcap.packets.enumerations.EthernetHeader;
import com.uh.nwvz.server.pcap.packets.enumerations.IPHeader;
import com.uh.nwvz.server.pcap.packets.enumerations.PacketHeader;
import com.uh.nwvz.server.pcap.packets.enumerations.TCPHeader;

public class PacketHandler implements PcapPacketHandler<StringBuilder> {

	private Integer packetKey = 1;

	private Integer flowKey = 1;

	private final Ethernet ethernet = new Ethernet();

	private final Ip4 ip4 = new Ip4();

	private final Ip6 ip6 = new Ip6();

	private final Tcp tcp = new Tcp();

	private final Http http = new Http();

	private final Icmp icmp = new Icmp();

	private final Udp udp = new Udp();

	private Map<Integer, Packet> packets = new TreeMap<Integer, Packet>();

	private long packetCount = 0;

	private long flowCount = 0;

	private final Map<JFlowKey, Flow> flows = new HashMap<JFlowKey, Flow>();

	private final Map<Integer, Flow> iFlows = new TreeMap<Integer, Flow>();

	@Override
	public void nextPacket(PcapPacket packet, StringBuilder errbuf) {

		packetCount++;

		Date receiveDate = new Date(packet.getCaptureHeader()
				.timestampInMillis());
		int size = packet.getTotalSize();

		JFlowKey key = packet.getState().getFlowKey();

		Flow flow = flows.get(key);

		if (flow == null) {
			flow = new Flow(flowKey);
			flows.put(key, flow);
			iFlows.put(flowKey, flow);
			flowKey++;
			flowCount++;
		}

		Packet iPacket = new Packet(packetKey, flow.getId(), receiveDate, size);

		if (packet.hasHeader(Ethernet.ID)) {
			packet.getHeader(ethernet);

			EthernetPacket ethernetPacket = new EthernetPacket(
					0, ethernet.destination(),
					ethernet.source(), ethernet.type());

			iPacket.setHeader(PacketHeader.ETHERNET);
			iPacket.setSubPacket(ethernetPacket);

			if (packet.hasHeader(Ip4.ID)) {
				packet.getHeader(ip4);

				IP4Packet ip4Packet = new IP4Packet(0,
						ip4.destination(), ip4.source(), ip4.flags(),
						ip4.version(), ip4.type());

				ethernetPacket.setHeader(EthernetHeader.IP4);
				ethernetPacket.setSubPacket(ip4Packet);

				if (packet.hasHeader(Tcp.ID)) {
					packet.getHeader(tcp);

					TcpPacket tcpPacket = new TcpPacket(tcp.ack(),
							tcp.destination(), tcp.source(), tcp.destination(),
							tcp.flags(), tcp.window());

					ip4Packet.setHeader(IPHeader.TCP);
					ip4Packet.setSubPacket(tcpPacket);

					if (packet.hasHeader(Http.ID)) {
						packet.getHeader(http);

						HttpPacket httpPacket = new HttpPacket(
								http.contentType(),
								http.fieldValue(Http.Request.Referer));

						tcpPacket.setHeader(TCPHeader.HTTP);
						tcpPacket.setSubPacket(httpPacket);
					}
				} else if (packet.hasHeader(Udp.ID)) {
					packet.getHeader(udp);

					UdpPacket udpPacket = new UdpPacket(0,
							udp.destination(), udp.source(), udp.length());

					ip4Packet.setHeader(IPHeader.UDP);
					ip4Packet.setSubPacket(udpPacket);
				} else if (packet.hasHeader(Icmp.ID)) {
					packet.getHeader(icmp);

					ICMPPacket icmpPacket = new ICMPPacket(0,
							icmp.code(), icmp.type());

					ip4Packet.setHeader(IPHeader.ICMP);
					ip4Packet.setSubPacket(icmpPacket);
				}
			} else if (packet.hasHeader(Ip6.ID)) {
				packet.getHeader(ip6);

				IP6Packet ip6Packet = new IP6Packet(ip6.source(),
						ip6.destination(), ip6.version());

				ethernetPacket.setHeader(EthernetHeader.IP6);
				ethernetPacket.setSubPacket(ip6Packet);
			}
		}

		packets.put(packetKey, iPacket);
		flow.addPacket(iPacket);
		packetKey++;

	}

	public long getPacketCount() {
		return packetCount;
	}

	public Map<Integer, Packet> getPackets() {
		return packets;
	}

	public long getFlowCount() {
		return flowCount;
	}

}
