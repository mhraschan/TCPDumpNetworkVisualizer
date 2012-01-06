package com.uh.nwvz.server;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uh.nwvz.client.PacketTransmissionService;
import com.uh.nwvz.server.common.Commons;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketTransmissionServiceImpl extends RemoteServiceServlet
		implements PacketTransmissionService {

	private static final long serialVersionUID = -7400779360249636037L;

	private HttpSession getSession() {
		return this.getThreadLocalRequest().getSession(false);
	}

	@Override
	public PacketInfoDTO getPacketInfo() {
		HttpSession session = getSession();

		if (session != null) {
			Long firstPacketTime = (Long) session
					.getAttribute(Commons.SESSION_FIRST_PACKET_TIME);
			Long lastPacketTime = (Long) session
					.getAttribute(Commons.SESSION_LAST_PACKET_TIME);
			Long totalPacketCount = (Long) session
					.getAttribute(Commons.SESSION_TOTAL_PACKET_COUNT);

			PacketInfoDTO packetInfo = new PacketInfoDTO(firstPacketTime,
					lastPacketTime, totalPacketCount);

			return packetInfo;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SimplePacketDTO[] nextPackets() {
		HttpSession session = getSession();

		if (session != null) {
			int packetTransferred = (Integer) session
					.getAttribute(Commons.SESSION_CURRENT_TRANSFERED_PACKETS);
			int totalPacketsToTransfer = (Integer) session
					.getAttribute(Commons.SESSION_PACKETS_TO_TRANSFER);

			List<SimplePacketDTO> packets = ((List<SimplePacketDTO>) session
					.getAttribute(Commons.SESSION_SIMPLE_PACKET_LIST));

			int toTransfer = Commons.PACKET_TRANSFER_SIZE;

			if (packetTransferred + Commons.PACKET_TRANSFER_SIZE > totalPacketsToTransfer)
				toTransfer = totalPacketsToTransfer - packetTransferred;

			List<SimplePacketDTO> retList = packets.subList(packetTransferred,
					packetTransferred + toTransfer);

			session.setAttribute(Commons.SESSION_CURRENT_TRANSFERED_PACKETS,
					packetTransferred + toTransfer);

			SimplePacketDTO[] retArray = new SimplePacketDTO[toTransfer];
			retArray = retList.toArray(retArray);

			return retArray;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PacketTransferInfoDTO startPacketTransfer(long endDate) {
		HttpSession session = getSession();

		if (session != null) {
			session.setAttribute(Commons.SESSION_CURRENT_TRANSFERED_PACKETS, 0);

			Long firstPacketTime = (Long) session
					.getAttribute(Commons.SESSION_FIRST_PACKET_TIME);
			Long lastPacketTime = (Long) session
					.getAttribute(Commons.SESSION_LAST_PACKET_TIME);

			if (endDate > lastPacketTime || endDate < firstPacketTime) {
				return null;
			}

			Date packetTime = new Date(endDate);
			List<SimplePacketDTO> packets = ((List<SimplePacketDTO>) session
					.getAttribute(Commons.SESSION_SIMPLE_PACKET_LIST));
			int packetCount = 0;

			for (SimplePacketDTO packet : packets) {
				if (packet.getReceiveDate().before(packetTime))
					packetCount++;
				else
					break;
			}

			if (packetCount == 0)
				return null;

			session.setAttribute(Commons.SESSION_PACKETS_TO_TRANSFER,
					packetCount);

			return new PacketTransferInfoDTO(packetCount, false);
		}

		return null;
	}
}
