package com.uh.nwvz.client.gfx;

import java.util.Iterator;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.commons.Vector;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.client.gfx.graph.Graph;
import com.uh.nwvz.client.gfx.graph.Node;

public class GfxManager {
	private Canvas canvas = null;
	
	private Graph g = new Graph();
	
	public GfxManager(Canvas canvas) {
		this.canvas = canvas;
		
		dummyInit();
	}
	
	private void dummyInit() {	
		// paint two nodes and an association
		Vector center = new Vector(50,50);
		Node a = new Node(center, 30, "ads.google.at", CssColor.make(255,128,128), CssColor.make(255,146,146), CssColor.make(255,100,100));
		g.addNode(a);
		center = new Vector(200,200);
		Node b = new Node(center, 40, "localhost", CssColor.make(128,255,128), CssColor.make(146,255,146), CssColor.make(100,255,100));
		g.addNode(b);
		Association ass = new Association(a, b);
		g.addAssociation(ass);
	}
	
	public void paint() {
		Context2d context = canvas.getContext2d();
		context.clearRect(0,  0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
		
		Iterator<Node> itNodes = g.getNodes().iterator();
		while (itNodes.hasNext()) {
			Node n = itNodes.next();
			n.draw(context);
		}
		
		Iterator<Association> itAssociation = g.getAssociations().iterator();
		while (itAssociation.hasNext()) {
			Association a = itAssociation.next();
			a.draw(context);
		}
	}
}
