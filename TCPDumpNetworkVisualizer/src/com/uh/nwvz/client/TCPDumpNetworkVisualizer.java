package com.uh.nwvz.client;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.kiouri.sliderbar.client.view.SliderBarHorizontal;
import com.uh.nwvz.client.commons.LogListener;
import com.uh.nwvz.client.commons.PacketReceiveNotifier;
import com.uh.nwvz.client.commons.async.PacketCountAsyncCallback;
import com.uh.nwvz.client.components.LogTextArea;
import com.uh.nwvz.client.components.TimeSlider;
import com.uh.nwvz.client.gfx.CanvasEventManager;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.commons.Size;
import com.uh.nwvz.client.network.GraphBuilder;
import com.uh.nwvz.client.network.NetworkNodeFactory;
import com.uh.nwvz.client.network.PacketManager;
import com.uh.nwvz.shared.dto.SimplePacketDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TCPDumpNetworkVisualizer implements EntryPoint,
		PacketReceiveNotifier, IUploader.OnFinishUploaderHandler, LogListener {

	private ListBox lbFlowMap = new ListBox();
	private Canvas cvGraph = null;
	private Label lblPacketCount = new Label();
	private ListBox lbPackets = new ListBox();
	private LogTextArea rtLog = new LogTextArea();
	private Button startButton = new Button("Start");
	private Button stopButton = new Button("Stop");
	private TextBox tbCurrentPacket = new TextBox();
	private Button btnSetCurrentPacket = new Button("Set current packet");
	private TimeSlider timeAxisSlider = new TimeSlider(100, "1000px");
	
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
		RootPanel.get("flow_map").add(lbFlowMap);

		// Create a new uploader panel and attach it to the document
		MultiUploader defaultUploader = new MultiUploader();
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.add(defaultUploader);
		flowPanel.add(lblPacketCount);
		flowPanel.add(lbPackets);
		flowPanel.add(rtLog);
		flowPanel.add(startButton);
		flowPanel.add(stopButton);
		flowPanel.add(tbCurrentPacket);
		flowPanel.add(btnSetCurrentPacket);
		RootPanel.get("default").add(flowPanel);
		
		startButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				packetManager.start();
			}
			
		});
		
		stopButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				packetManager.stop();
			}
			
		});
		
		btnSetCurrentPacket.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				packetManager.forceDrawPackets(Integer.valueOf(tbCurrentPacket.getText()));
			}
			
		});

		// create Graphing Canvas
		cvGraph = Canvas.createIfSupported();
		cvGraph.setCoordinateSpaceHeight(500);
		cvGraph.setCoordinateSpaceWidth(1000);

		// initialize event manager
		CanvasEventManager.initCanvasEventManager(cvGraph);
		NetworkNodeFactory.initNetworkNodeFactory(new Size(cvGraph.getCoordinateSpaceWidth(), cvGraph.getCoordinateSpaceHeight()));

		// initialize graphics manager
		gfxManager = new GfxManager(cvGraph);
		graphBuilder = new GraphBuilder(gfxManager);
		packetManager = new PacketManager(graphBuilder, this);
		
		timeAxisSlider.addBarValueChangedHandler(new BarValueChangedHandler() {

			@Override
			public void onBarValueChanged(BarValueChangedEvent event) {
				packetManager.setTime(event.getValue());	
			}
			
		});

		RootPanel.get("graph_canvas").add(cvGraph);
		RootPanel.get("slider").add(timeAxisSlider);

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
							this, packetTransmissionSvc));
		} else {
			rtLog.logError("File-upload failed");
		}
	}

	@Override
	public void received(SimplePacketDTO[] packets) {
		packetManager.addPackets(packets);
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
