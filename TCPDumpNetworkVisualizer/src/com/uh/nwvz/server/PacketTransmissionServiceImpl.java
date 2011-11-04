package com.uh.nwvz.server;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uh.nwvz.client.PacketTransmissionService;
import com.uh.nwvz.server.common.Commons;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketTransmissionServiceImpl extends RemoteServiceServlet
		implements PacketTransmissionService {

	private static final long serialVersionUID = -7400779360249636037L;

	private HttpSession getSession() {
		return this.getThreadLocalRequest().getSession(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getTotalPacketCount() {
		HttpSession session = getSession();

		if (session != null) {
			return ((List<SimplePacketDTO>) session
					.getAttribute(Commons.SESSION_SIMPLE_PACKET_LIST)).size();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SimplePacketDTO[] getNextPackets() {
		HttpSession session = getSession();

		if (session != null) {
			Integer sentPackets = (Integer) session
					.getAttribute(Commons.SESSION_PACKETS_SENT);

			List<SimplePacketDTO> packets = ((List<SimplePacketDTO>) session
					.getAttribute(Commons.SESSION_SIMPLE_PACKET_LIST));

			if (sentPackets < packets.size()) {
				int fromPackets = sentPackets;
				int toPackets = sentPackets + Commons.PACKET_TRANSFER_SIZE;
				
				if (toPackets > packets.size())
					toPackets = packets.size();

				session.setAttribute(Commons.SESSION_PACKETS_SENT, toPackets);

				List<SimplePacketDTO> retList = packets.subList(fromPackets,
						toPackets);
				SimplePacketDTO[] retArray = new SimplePacketDTO[Commons.PACKET_TRANSFER_SIZE];
				retArray = retList.toArray(retArray);

				return retArray;
			}
		}

		return null;
	}
}
