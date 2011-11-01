package com.uh.nwvz.shared.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class FlowDTO implements Serializable {

	private static final long serialVersionUID = 4823516335251345606L;

	private Map<String, PacketDTO> flows = new TreeMap<String, PacketDTO>();
}
