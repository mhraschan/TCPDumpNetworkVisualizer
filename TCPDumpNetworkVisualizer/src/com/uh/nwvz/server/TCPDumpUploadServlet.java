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
import com.uh.nwvz.server.pcap.SimplePacketHandler;
import com.uh.nwvz.shared.PcapException;
import com.uh.nwvz.shared.dto.PacketInfoDTO;

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

					final Pcap pcap = Pcap.openOffline(file.getAbsolutePath(),
							errbuf);
					if (pcap == null) {
						throw new PcapException("Failed to open uploaded file!");
					} else {
						SimplePacketHandler packetHandler = new SimplePacketHandler();

						pcap.loop(Pcap.LOOP_INFINITE, packetHandler, errbuf);

						HttpSession session = request.getSession();
						session.setAttribute(Commons.SESSION_NODE_LIST,
								packetHandler.getNodes());

						PacketInfoDTO packetInfo = new PacketInfoDTO(
								packetHandler.getFirstPacketDate(),
								packetHandler.getLastPacketDate(),
								packetHandler.getTotalPacketCount(),
								packetHandler.getTcpPacketCount(),
								packetHandler.getNodeCount());

						session.setAttribute(Commons.SESSION_PACKET_INFO,
								packetInfo);

						response = "Total packet count: "
								+ packetHandler.getTotalPacketCount()
								+ "\nNode count: "
								+ packetHandler.getNodeCount()
								+ "\nTCP packets: "
								+ packetHandler.getTcpPacketCount();
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
