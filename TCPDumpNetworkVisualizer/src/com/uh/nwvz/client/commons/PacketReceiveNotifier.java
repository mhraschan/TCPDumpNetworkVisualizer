package com.uh.nwvz.client.commons;

import com.uh.nwvz.shared.dto.SimplePacketDTO;

public interface PacketReceiveNotifier {

	public void received(SimplePacketDTO[] packets);

}
