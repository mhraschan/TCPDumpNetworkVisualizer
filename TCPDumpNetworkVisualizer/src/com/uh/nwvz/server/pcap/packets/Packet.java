package com.uh.nwvz.server.pcap.packets;

import java.util.Date;

import com.uh.nwvz.server.pcap.packets.enumerations.PacketHeader;
import com.uh.nwvz.shared.SimplePacketType;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

public class Packet {

	private int id;
	
	private int flowId;

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	private Date receiveDate;

	private int size;

	private PacketHeader header;

	private GeneralPacket subPacket;

	public Packet(Date receiveDate, int size) {
		super();
		this.receiveDate = receiveDate;
		this.size = size;
	}

	public Packet(Date receiveDate, int size, PacketHeader header) {
		super();
		this.receiveDate = receiveDate;
		this.size = size;
		this.header = header;
	}

	public Packet(int id, int flowId, Date receiveDate, int size) {
		super();
		this.id = id;
		this.flowId = flowId;
		this.receiveDate = receiveDate;
		this.size = size;
	}

	public PacketHeader getHeader() {
		return header;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public int getSize() {
		return size;
	}

	public GeneralPacket getSubPacket() {
		return subPacket;
	}

	public void setHeader(PacketHeader header) {
		this.header = header;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSubPacket(GeneralPacket subPacket) {
		this.subPacket = subPacket;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public SimplePacketDTO convertToSimplePacket() {
		SimplePacketDTO simplePacket = new SimplePacketDTO();
		simplePacket.setPacketId(id);
		simplePacket.setFlowId(flowId);
		simplePacket.setSize(size);
		simplePacket.setType(SimplePacketType.UNKOWN);
		
		if (subPacket != null) {
			subPacket.fillSimplePacket(simplePacket);
		}
		
		return simplePacket;
	}
	
}
