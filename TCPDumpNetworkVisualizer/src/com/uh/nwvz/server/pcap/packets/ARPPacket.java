package com.uh.nwvz.server.pcap.packets;

import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class ARPPacket implements GeneralPacket {

	private int hardwareType;

	private String hardwareTypeDescritpion;

	private int protocolType;

	private String protocolTypeDescription;

	private int operation;

	private String operationDescription;

	public ARPPacket(int hardwareType, String hardwareTypeDescritpion,
			int protocolType, String protocolTypeDescription, int operation,
			String operationDescription) {
		super();
		this.hardwareType = hardwareType;
		this.hardwareTypeDescritpion = hardwareTypeDescritpion;
		this.protocolType = protocolType;
		this.protocolTypeDescription = protocolTypeDescription;
		this.operation = operation;
		this.operationDescription = operationDescription;
	}

	public int getHardwareType() {
		return hardwareType;
	}

	public String getHardwareTypeDescritpion() {
		return hardwareTypeDescritpion;
	}

	public int getOperation() {
		return operation;
	}

	public String getOperationDescription() {
		return operationDescription;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public String getProtocolTypeDescription() {
		return protocolTypeDescription;
	}

	public void setHardwareType(int hardwareType) {
		this.hardwareType = hardwareType;
	}

	public void setHardwareTypeDescritpion(String hardwareTypeDescritpion) {
		this.hardwareTypeDescritpion = hardwareTypeDescritpion;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public void setProtocolTypeDescription(String protocolTypeDescription) {
		this.protocolTypeDescription = protocolTypeDescription;
	}

	@Override
	public boolean hasSubPacket() {
		return false;
	}

	@Override
	public void fillSimplePacket(SimplePacketDTO simplePacket) {
	}

}
