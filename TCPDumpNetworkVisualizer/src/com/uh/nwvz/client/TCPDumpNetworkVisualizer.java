package com.uh.nwvz.client;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TCPDumpNetworkVisualizer implements EntryPoint {

	private ListBox lbFlowMap = new ListBox();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Attach the image viewer to the document
		RootPanel.get("flow_map").add(lbFlowMap);

		// Create a new uploader panel and attach it to the document
		MultiUploader defaultUploader = new MultiUploader();
		RootPanel.get("default").add(defaultUploader);

		// Add a finish handler which will load the image once the upload
		// finishes
		defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
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
