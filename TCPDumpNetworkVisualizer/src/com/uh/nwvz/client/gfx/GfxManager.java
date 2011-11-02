package com.uh.nwvz.client.gfx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.commons.Association;
import com.uh.nwvz.client.gfx.commons.Node;
import com.uh.nwvz.client.gfx.commons.Vector;

public class GfxManager {
	private Canvas canvas = null;
	
	private List<Node> nodes = null;
	private List<Association> associations = null;
	
	public GfxManager(Canvas canvas) {
		this.canvas = canvas;
		
		dummyInit();
	}
	
	private void dummyInit() {
		nodes = new ArrayList<Node>();
		associations = new ArrayList<Association>();
		
		// paint two nodes and an association
		Vector center = new Vector(50,50);
		Node a = new Node(center, 20, CssColor.make(255,0,0));
		nodes.add(a);
		center = new Vector(100,100);
		Node b = new Node(center, 30, CssColor.make(0,255,0));
		nodes.add(b);
		Association ass = new Association(a, b);
		associations.add(ass);
	}
	
	public void paint() {
		Context2d context = canvas.getContext2d();
		context.clearRect(0,  0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
		
		Iterator<Node> itNodes = nodes.iterator();
		while (itNodes.hasNext()) {
			Node n = itNodes.next();
			n.draw(context);
		}
		
		Iterator<Association> itAssociation = associations.iterator();
		while (itAssociation.hasNext()) {
			Association a = itAssociation.next();
			a.draw(context);
		}
	}
}
