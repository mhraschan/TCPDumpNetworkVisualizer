package com.uh.nwvz.client;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.commons.async.PacketCountAsyncCallback;
import com.uh.nwvz.client.components.LogTextArea;
import com.uh.nwvz.client.gfx.CanvasEventManager;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.shared.PcapUtil;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TCPDumpNetworkVisualizer implements EntryPoint,
		PacketReceiveNotifier, IUploader.OnFinishUploaderHandler {

	private ListBox lbFlowMap = new ListBox();
	private Canvas cvGraph = null;
	private Label lblPacketCount = new Label();
	private ListBox lbPackets = new ListBox();
	private LogTextArea rtLog = new LogTextArea();

	// timer refresh rate, in milliseconds
	private final int refreshRate = 25;

	private GfxManager gfxManager = null;

	PacketTransmissionServiceAsync packetTransmissionSvc = GWT
			.create(PacketTransmissionService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Attach the image viewer to the document
		RootPanel.get("flow_map").add(lbFlowMap);

		// Create a new uploader panel and attach it to the document
		MultiUploader defaultUploader = new MultiUploader();
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.add(defaultUploader);
		flowPanel.add(lblPacketCount);
		flowPanel.add(lbPackets);
		flowPanel.add(rtLog);
		RootPanel.get("default").add(flowPanel);

		// create Graphing Canvas
		cvGraph = Canvas.createIfSupported();
		cvGraph.setCoordinateSpaceHeight(500);
		cvGraph.setCoordinateSpaceWidth(1000);

		// initialize event manager
		CanvasEventManager.initCanvasEventManager(cvGraph);

		// initialize graphics manager
		gfxManager = new GfxManager(cvGraph);

		RootPanel.get("graph_canvas").add(cvGraph);

		final Timer timer = new Timer() {
			@Override
			public void run() {
				updateGfx();
			}
		};
		timer.scheduleRepeating(refreshRate);

		// Add a finish handler which will load the image once the upload
		// finishes
		defaultUploader.addOnFinishUploadHandler(this);
	}

	private void updateGfx() {
		gfxManager.paint();
	}

	@Override
	public void onFinish(IUploader uploader) {
		if (uploader.getStatus() == Status.SUCCESS) {

			UploadedInfo info = uploader.getServerInfo();
			rtLog.logInfo("File name " + info.name);
			rtLog.logInfo("File content-type " + info.ctype);
			rtLog.logInfo("File size " + info.size);
			rtLog.logInfo("Server message " + info.message);

			packetTransmissionSvc
					.getTotalPacketCount(new PacketCountAsyncCallback(this,
							rtLog, packetTransmissionSvc));
		} else {
			rtLog.logError("File-upload failed");
		}
	}

	@Override
	public void received(SimplePacketDTO[] packets) {
		for (SimplePacketDTO packet : packets) {
			lbPackets.addItem(PcapUtil.ip(packet.getSource()) + "("
					+ packet.getSourceHostname() + ") -> "
					+ PcapUtil.ip(packet.getDestination()) + "("
					+ packet.getDestHostname() + "); FlowId: "
					+ packet.getFlowId());
		}
	}

}
