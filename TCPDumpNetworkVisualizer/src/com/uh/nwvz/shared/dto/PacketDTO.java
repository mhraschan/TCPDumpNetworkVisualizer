package com.uh.nwvz.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PacketDTO implements Serializable {

	private static final long serialVersionUID = 1287563480565724839L;

	private Date receiveDate;

	private FlowDTO flow;

	public PacketDTO() {
		super();
	}

	public PacketDTO(Date receiveDate) {
		super();
		this.receiveDate = receiveDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public FlowDTO getFlow() {
		return flow;
	}

	public void setFlow(FlowDTO flow) {
		this.flow = flow;
	}

	@Override
	public String toString() {
		return "PacketDTO [receiveDate=" + receiveDate + "]";
	}

}
