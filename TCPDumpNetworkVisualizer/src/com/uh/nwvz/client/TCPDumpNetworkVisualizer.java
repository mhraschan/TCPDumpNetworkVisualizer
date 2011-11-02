package com.uh.nwvz.client;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.uh.nwvz.client.gfx.CanvasEventManager;
import com.uh.nwvz.client.gfx.GfxManager;
import com.uh.nwvz.client.gfx.commons.Association;
import com.uh.nwvz.client.gfx.commons.Node;
import com.uh.nwvz.client.gfx.commons.Vector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TCPDumpNetworkVisualizer implements EntryPoint {

	private ListBox lbFlowMap = new ListBox();
	private Canvas cvGraph = null;
	
	//timer refresh rate, in milliseconds
	private final int refreshRate = 25;
	
	private GfxManager gfxManager = null;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		// Attach the image viewer to the document
		RootPanel.get("flow_map").add(lbFlowMap);

		// Create a new uploader panel and attach it to the document
		MultiUploader defaultUploader = new MultiUploader();
		RootPanel.get("default").add(defaultUploader);
		
		// create Graphing Canvas
		cvGraph = Canvas.createIfSupported();
		
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
		defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
	}
	
	private void updateGfx() {
		gfxManager.paint();
	}

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {

				// The server sends useful information to the client by default
				UploadedInfo info = uploader.getServerInfo();
				System.out.println("File name " + info.name);
				System.out.println("File content-type " + info.ctype);
				System.out.println("File size " + info.size);

				// You can send any customized message and parse it
				System.out.println("Server message " + info.message);
			}

		}
	};

}
