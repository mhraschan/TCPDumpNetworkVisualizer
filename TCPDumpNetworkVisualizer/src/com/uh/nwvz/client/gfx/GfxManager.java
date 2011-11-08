package com.uh.nwvz.client.gfx;

import java.util.Iterator;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.commons.Size;
import com.uh.nwvz.client.gfx.commons.Vector;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.client.gfx.graph.Graph;
import com.uh.nwvz.client.gfx.graph.Node;
import com.uh.nwvz.client.gfx.graph.layout.ForceDirectedLayout;
import com.uh.nwvz.client.gfx.graph.layout.ILayout;

public class GfxManager {
	private Canvas canvas = null;
	
	private Graph graph = new Graph();
	private ILayout layout = null;
	
	public GfxManager(Canvas canvas) {
		this.canvas = canvas;
		
		this.layout = new ForceDirectedLayout();
		this.layout.setCanvasSize(new Size(canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight()));
		this.layout.setGraph(graph);
		
		dummyInit();
	}
	
	private void dummyInit() {	
		// paint two nodes and an association
		Vector center = new Vector(50,50);
		Node a = new Node(center, 30, "ads.google.at", CssColor.make(255,128,128), CssColor.make(255,146,146), CssColor.make(255,100,100));
		graph.addNode(a);
		center = new Vector(200,200);
		Node b = new Node(center, 40, "localhost", CssColor.make(128,255,128), CssColor.make(146,255,146), CssColor.make(100,255,100));
		graph.addNode(b);
		
		Node c = new Node(center, 20, "www.google.com", CssColor.make(255,128,128), CssColor.make(255,146,146), CssColor.make(255,100,100));
		graph.addNode(c);
		
		Association ass = new Association(b, a);
		graph.addAssociation(ass);
		
		ass = new Association(b,c);
		graph.addAssociation(ass);
		
		ass = new Association(c, a);
		graph.addAssociation(ass);
		
		
		this.layout.performLayout();
	}
	
	public void paint() {		
		Context2d context = canvas.getContext2d();
		context.clearRect(0,  0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
		
		// Draw associations first
		Iterator<Association> itAssociation = graph.getAssociations().iterator();
		while (itAssociation.hasNext()) {
			Association a = itAssociation.next();
			a.drawBackground(context);
		}
		
		// node background
		Iterator<Node> itNodes = graph.getNodes().iterator();
		while (itNodes.hasNext()) {
			Node n = itNodes.next();
			n.drawBackground(context);
		}
		
		// node foreground
		itNodes = graph.getNodes().iterator();
		while (itNodes.hasNext()) {
			Node n = itNodes.next();
			n.drawForeground(context);
		}

	}
}
