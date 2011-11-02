package com.uh.nwvz.server;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uh.nwvz.client.PacketTransmissionService;
import com.uh.nwvz.server.common.Commons;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class PacketTransmissionServiceImpl extends RemoteServiceServlet
		implements PacketTransmissionService {

	private static final long serialVersionUID = -7400779360249636037L;

	@Override
	public List<SimplePacketDTO> getPackets() {
		return null;
	}

	private HttpSession getSession() {
		return this.getThreadLocalRequest().getSession(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getTotalPacketCount() {
		HttpSession session = getSession();

		if (session != null) {
			return ((Map<Integer, SimplePacketDTO>) session
					.getAttribute(Commons.SESSION_SIMPLE_PACKET_LIST)).size();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, SimplePacketDTO> getAllPackets() {
		HttpSession session = getSession();

		if (session != null) {
			return ((Map<Integer, SimplePacketDTO>) session
					.getAttribute(Commons.SESSION_SIMPLE_PACKET_LIST));
		}
		return null;
	}

	@Override
	public Map<Integer, SimplePacketDTO> getNextPackets() {
		HttpSession session = getSession();

		if (session != null) {
			Integer sentPackets = (Integer) session
					.getAttribute(Commons.SESSION_PACKETS_SENT);
			
			//TODO: sent next packets
		}

		return null;
	}

}
