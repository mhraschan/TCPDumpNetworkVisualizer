package com.uh.nwvz.server;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.uh.nwvz.client.PacketTransmissionService;
import com.uh.nwvz.server.common.Commons;
import com.uh.nwvz.shared.dto.PacketDTO;

public class PacketTransmissionServiceImpl extends RemoteServiceServlet
		implements PacketTransmissionService {

	private static final long serialVersionUID = -7400779360249636037L;

	@SuppressWarnings("unchecked")
	@Override
	public List<PacketDTO> getPackets() {
		HttpSession session = getSession();
		
		if (session != null) {
			return (List<PacketDTO>) session.getAttribute(Commons.SESSION_PACKET_LIST);
		}
		return null;
	}

	private HttpSession getSession() {
		return this.getThreadLocalRequest().getSession(false);
	}

}
