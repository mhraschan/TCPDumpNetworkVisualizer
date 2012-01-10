package com.uh.nwvz.client.network;

import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.ColorProvider;
import com.uh.nwvz.client.gfx.commons.Size;
import com.uh.nwvz.client.gfx.commons.Vector;
import com.uh.nwvz.client.gfx.graph.Node;

public class NetworkNodeFactory {
	private Size canvasSize = null;
	
	private static double ANGLE_INCREMENT = 2 * Math.PI / 12; // TODO: adaptive!
	
	// for precalculation of positions
	private double currentAngle = 0;
	private double radius = 0;
	
	private static NetworkNodeFactory singleton = null;
	
	private NetworkNodeFactory(Size size) {
		this.canvasSize = size;
		this.radius = Math.sqrt(size.getWidth()*size.getWidth() + size.getHeight() * size.getHeight()) / 7;
	}
	
	private NetworkNodeFactory() {
		
	}
	
	public static void initNetworkNodeFactory(Size canvasSize) {
		if (singleton == null)
			singleton = new NetworkNodeFactory(canvasSize);
	}
	
	public static NetworkNodeFactory getNetworkNodeFactory() {
		if (singleton == null)
			singleton = new NetworkNodeFactory();
		return singleton;
	}
	
	public NetworkNode createHomeNode() {
		Vector center = new Vector((int) canvasSize.getWidth()/2, (int) canvasSize.getHeight()/2);
		return new NetworkNode(center, 30, "home", ColorProvider.getNormalColor(Protocol.LOCALHOST), 
				ColorProvider.getMouseOverColor(Protocol.LOCALHOST), ColorProvider.getClickColor(Protocol.LOCALHOST));
	}
	
	private Vector getCenterPosition() {
		/*Vector center = new Vector((int) canvasSize.getWidth()/2, (int) canvasSize.getHeight()/2);
		Vector rVec = new Vector(radius * Math.cos(currentAngle), radius * Math.sin(currentAngle));
		center.add(rVec);*/
		Vector center = new Vector(Math.random()*canvasSize.getWidth(),Math.random()*canvasSize.getHeight());
		currentAngle += ANGLE_INCREMENT;
		return center;
	}
	
	public NetworkNode createHTTPNode(String name) {
		return new NetworkNode(getCenterPosition(), 20, name, ColorProvider.getNormalColor(Protocol.HTTP), 
				ColorProvider.getMouseOverColor(Protocol.HTTP), ColorProvider.getClickColor(Protocol.HTTP));
	}
	
	public NetworkNode createMixedNode(String name) {
		return new NetworkNode(getCenterPosition(), 20, name, ColorProvider.getNormalColor(Protocol.MIXED), 
				ColorProvider.getMouseOverColor(Protocol.MIXED), ColorProvider.getClickColor(Protocol.MIXED));
	}
	
	public NetworkNode createOtherNode(String name) {
		return new NetworkNode(getCenterPosition(), 20, name, ColorProvider.getNormalColor(Protocol.OTHER), 
				ColorProvider.getMouseOverColor(Protocol.OTHER), ColorProvider.getClickColor(Protocol.OTHER));
	}
	
	public NetworkNode createTCPNode(String name) {
		return new NetworkNode(getCenterPosition(), 20, name, ColorProvider.getNormalColor(Protocol.TCP), 
				ColorProvider.getMouseOverColor(Protocol.TCP), ColorProvider.getClickColor(Protocol.TCP));
	}
	
	public NetworkNode createUDPNode(String name) {
		return new NetworkNode(getCenterPosition(), 20, name, ColorProvider.getNormalColor(Protocol.UDP), 
				ColorProvider.getMouseOverColor(Protocol.UDP), ColorProvider.getClickColor(Protocol.UDP));
	}
	
	public NetworkNode createICMPNode(String name) {
		return new NetworkNode(getCenterPosition(), 20, name, ColorProvider.getNormalColor(Protocol.ICMP), 
				ColorProvider.getMouseOverColor(Protocol.ICMP), ColorProvider.getClickColor(Protocol.ICMP));
	}

}
