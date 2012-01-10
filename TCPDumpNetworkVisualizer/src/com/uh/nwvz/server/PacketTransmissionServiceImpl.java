package com.uh.nwvz.server;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uh.nwvz.client.PacketTransmissionService;
import com.uh.nwvz.server.common.Commons;
import com.uh.nwvz.shared.dto.NetworkNodeDTO;
import com.uh.nwvz.shared.dto.PacketInfoDTO;
import com.uh.nwvz.shared.dto.PacketTransferInfoDTO;

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
			PacketInfoDTO packetInfo = (PacketInfoDTO) session
					.getAttribute(Commons.SESSION_PACKET_INFO);

			return packetInfo;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NetworkNodeDTO nextNode() {
		HttpSession session = getSession();

		if (session != null) {
			int nodesTransferred = (Integer) session
					.getAttribute(Commons.SESSION_CURRENT_TRANSFERED_PACKETS);
			int totalNodesToTransfer = (Integer) session
					.getAttribute(Commons.SESSION_NODES_TO_TRANSFER);

			if (nodesTransferred >= totalNodesToTransfer)
				return null;

			List<NetworkNodeDTO> nodes = ((List<NetworkNodeDTO>) session
					.getAttribute(Commons.SESSION_NODE_LIST_TO_TRANSFER));

			session.setAttribute(Commons.SESSION_CURRENT_TRANSFERED_PACKETS,
					nodesTransferred + 1);

			return nodes.get(nodesTransferred + 1);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PacketTransferInfoDTO startPacketTransfer(long endDate) {
		HttpSession session = getSession();

		if (session != null) {
			session.setAttribute(Commons.SESSION_CURRENT_TRANSFERED_NODES, 0);

			Long firstPacketTime = (Long) session
					.getAttribute(Commons.SESSION_FIRST_PACKET_TIME);
			Long lastPacketTime = (Long) session
					.getAttribute(Commons.SESSION_LAST_PACKET_TIME);

			if (endDate > lastPacketTime || endDate < firstPacketTime) {
				return null;
			}

			List<NetworkNodeDTO> packets = ((List<NetworkNodeDTO>) session
					.getAttribute(Commons.SESSION_NODE_LIST));

			NodeFilterer filt = new NodeFilterer(packets);
			filt.nodesBefore(endDate);

			session.setAttribute(Commons.SESSION_NODE_LIST_TO_TRANSFER,
					filt.getFilteredNodes());
			session.setAttribute(Commons.SESSION_NODES_TO_TRANSFER,
					filt.getNodeCount());

			return new PacketTransferInfoDTO(filt.getNodeCount(),
					filt.getPacketCount(), false);
		}

		return null;
	}
}
