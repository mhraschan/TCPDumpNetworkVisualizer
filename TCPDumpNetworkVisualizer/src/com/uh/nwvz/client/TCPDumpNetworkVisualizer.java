package com.uh.nwvz.client;

import java.sql.Date;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.async.PacketInfoAsyncCallback;
import com.uh.nwvz.client.components.LogTextArea;
import com.uh.nwvz.client.components.TimeSlider;
import com.uh.nwvz.client.gfx.CanvasEventManager;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.commons.Size;
import com.uh.nwvz.client.network.GraphBuilder;
import com.uh.nwvz.client.network.NetworkNodeFactory;
import com.uh.nwvz.client.network.PacketManager;
import com.uh.nwvz.shared.dto.PacketInfoDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TCPDumpNetworkVisualizer implements EntryPoint,
		IUploader.OnFinishUploaderHandler, LogListener {

	// private ListBox lbFlowMap = new ListBox();
	private Canvas cvGraph = null;
	private LogTextArea rtLog = new LogTextArea();
	private TimeSlider timeAxisSlider = new TimeSlider(100, "1000px");
	private TextBox tbClientIp = new TextBox();
	private Label lbTime = new Label();
	
	final DialogBox errorBox = new DialogBox();

	// timer refresh rate, in milliseconds
	private final int refreshRate = 100;

	private GraphBuilder graphBuilder = null;
	private GfxManager gfxManager = null;
	private PacketManager packetManager = null;

	PacketTransmissionServiceAsync packetTransmissionSvc = GWT
			.create(PacketTransmissionService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Attach the image viewer to the document
		// RootPanel.get("flow_map").add(lbFlowMap);

		// Create a new uploader panel and attach it to the document
		MultiUploader defaultUploader = new MultiUploader();
		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new Label(
				"Choose the TCP-Dump file to visualize: "));
		flexTable.setWidget(0, 1, defaultUploader);
		// flexTable.setWidget(1, 0, lblPacketCount);
		// flowPanel.add(lblPacketCount);
		// flowPanel.add(lbPackets);
		flexTable.setWidget(1, 0, new Label("Log: "));
		flexTable.setWidget(1, 1, rtLog);
		flexTable.setWidget(2, 0, new Label("Client-IP Address: "));
		flexTable.setWidget(2, 1, tbClientIp);
		RootPanel.get("default").add(flexTable);

		// create Graphing Canvas
		cvGraph = Canvas.createIfSupported();
		cvGraph.setCoordinateSpaceHeight(500);
		cvGraph.setCoordinateSpaceWidth(1000);

		// initialize event manager
		CanvasEventManager.initCanvasEventManager(cvGraph);
		NetworkNodeFactory
				.initNetworkNodeFactory(new Size(cvGraph
						.getCoordinateSpaceWidth(), cvGraph
						.getCoordinateSpaceHeight()));

		// initialize graphics manager
		gfxManager = new GfxManager(cvGraph);
		graphBuilder = new GraphBuilder(gfxManager);
		packetManager = new PacketManager(graphBuilder, packetTransmissionSvc,
				this);

		timeAxisSlider.addBarValueChangedHandler(new BarValueChangedHandler() {

			@Override
			public void onBarValueChanged(BarValueChangedEvent event) {
				if (tbClientIp.getText().isEmpty()) {
					errorBox.setText("Client-IP is empty!");
					//errorBox.show();
				} else {
					packetManager.setClientIpAddress(tbClientIp.getText());
					packetManager.setTime(event.getValue());
					PacketInfoDTO packetInfo = packetManager.getPacketInfo();
					if (packetInfo != null) {
						long totalTime = packetInfo.getLastPacketArrival() - packetInfo.getFirstPacketArrival();
						long currentTime = (long) ((totalTime * event.getValue()) / 100);
						DateTimeFormat format = DateTimeFormat.getFormat("mm:ss.SSS");
						lbTime.setText("Time: " + format.format(new Date(currentTime)));
					}
				}
			}

		});
		
		lbTime.setText("Time: 0:00");

		RootPanel.get("graph_canvas").add(cvGraph);
		RootPanel.get("slider").add(timeAxisSlider);
		RootPanel.get("slider").add(lbTime);

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

			uploader.setEnabled(false);

			packetTransmissionSvc.getPacketInfo(new PacketInfoAsyncCallback(
					packetManager, this));
		} else {
			rtLog.logError("File-upload failed");
		}
	}

	@Override
	public void logError(String message) {
		rtLog.logError(message);
	}

	@Override
	public void logWarn(String message) {
		rtLog.logWarn(message);
	}

	@Override
	public void logInfo(String message) {
		rtLog.logInfo(message);
	}

}
