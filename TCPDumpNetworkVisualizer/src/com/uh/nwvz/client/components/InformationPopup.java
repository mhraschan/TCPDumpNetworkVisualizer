package com.uh.nwvz.client.components;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;

public class InformationPopup extends PopupPanel {
	
	private FlexTable infoTable = new FlexTable();
	
	public InformationPopup() {
      super(true);

      infoTable.setText(0, 0, "Host:");
      infoTable.setText(1, 0, "Hostname:");
      infoTable.setText(2, 0, "Packets sent:");
      infoTable.setText(3, 0, "Packets received:");
      infoTable.setText(4, 0, "KByte sent:");
      infoTable.setText(5, 0, "KByte received:");
      infoTable.setText(6, 0, "Protocols: ");
      
      setWidget(infoTable);
	
      setGlassEnabled(true);
      center();
    }
	
	public void setData(String host, String hostname, int packetsSent, int packetsReceived, 
			long kbSent, long kbReceived, String protocols) {
		
		infoTable.setText(0, 1, host);
		infoTable.setText(1, 1, hostname);
		infoTable.setText(2, 1, String.valueOf(packetsSent));
		infoTable.setText(3, 1, String.valueOf(packetsReceived));
		infoTable.setText(4, 1, String.valueOf(kbSent));
		infoTable.setText(5, 1, String.valueOf(kbReceived));
	    infoTable.setText(6, 1, protocols);
	}
}
