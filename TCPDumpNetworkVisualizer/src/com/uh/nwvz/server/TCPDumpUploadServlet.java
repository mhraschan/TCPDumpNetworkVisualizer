package com.uh.nwvz.server;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.jnetpcap.Pcap;

import com.uh.nwvz.server.common.Commons;
import com.uh.nwvz.server.pcap.PacketHandler;
import com.uh.nwvz.shared.PcapException;

public class TCPDumpUploadServlet extends UploadAction {

	private static final long serialVersionUID = -511885767884970614L;
	
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					File file = File.createTempFile("upload-", ".bin");
					item.write(file);
					
					StringBuilder errbuf = new StringBuilder();
					
					final Pcap pcap = Pcap.openOffline(file.getAbsolutePath(), errbuf);
					if (pcap == null) {
						throw new PcapException("Failed to open uploaded file!");
					} else {
						PacketHandler packetHandler = new PacketHandler();
						
						pcap.loop(Pcap.LOOP_INFINITE, packetHandler, errbuf);
						
						HttpSession session = request.getSession();
						session.setAttribute(Commons.SESSION_PACKET_LIST, packetHandler.getPackets());
						
						response = "Total packet count: " + packetHandler.getPacketCount();
					}
				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}
		}

		removeSessionFileItems(request);

		return response;
	}
	
	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	}
}
