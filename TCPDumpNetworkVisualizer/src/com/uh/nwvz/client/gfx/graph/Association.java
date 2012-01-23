package com.uh.nwvz.client.gfx.graph;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;
import com.uh.nwvz.client.gfx.CanvasEventManager;
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.Vector;

public class Association implements IGfxObject {
	
	private Node startNode;
	private Node endNode;
	
	private boolean mouseOver = false;
	
	public Association(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
		startNode.addAssociation(this);
		endNode.addAssociation(this);
		CanvasEventManager mgr = CanvasEventManager.getCanvasEventManager();
		mgr.addMouseOverListener(this);
	}
	
	@Override
	public void unregisterEventListeners() {
		CanvasEventManager mgr = CanvasEventManager.getCanvasEventManager();
		mgr.addMouseOverListener(this);
	}

	
	
	public Node getStartNode() {
		return startNode;
	}



	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}



	public Node getEndNode() {
		return endNode;
	}



	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	
	@Override
	public void drawForeground(Context2d context) {
		// nothing to be done now
	}

	@Override
	public void drawBackground(Context2d context) {
		Vector centerStart = startNode.getCenter();
		Vector centerEnd = endNode.getCenter();
		double phi = Math.atan2((centerStart.getY() - centerEnd.getY()), (centerStart.getX()-centerEnd.getX()));
		//double phi = Math.atan((centerStart.getY() - centerEnd.getY())/(centerStart.getX() - centerEnd.getX()));
		
		
		context.setLineJoin(LineJoin.ROUND);
		context.setLineCap(LineCap.ROUND);
		if (mouseOver)
			context.setLineWidth(1.7);
		else
			context.setLineWidth(1.2);
		
		context.beginPath();
		context.moveTo(centerStart.getX() - startNode.getRadius() * Math.cos(phi),
				centerStart.getY() - startNode.getRadius() * Math.sin(phi));
		context.lineTo(centerEnd.getX() + endNode.getRadius() * Math.cos(phi),
				centerEnd.getY() + endNode.getRadius() * Math.sin(phi));
		context.closePath();
		context.stroke();
		
	}

	@Override
	public boolean isInsideObject(Vector point) {
		// calculate coefficients of equation representing the linear function
		Vector centerStart = startNode.getCenter();
		Vector centerEnd = endNode.getCenter();
		
		double a = centerEnd.getY() - centerStart.getY();
		double b = centerStart.getX() - centerEnd.getX();
		double c = centerStart.getY() * centerEnd.getX() - centerStart.getX() * centerEnd.getY();
		
		double distance = (a*point.getX() + b*point.getY() + c) / Math.sqrt(a*a + b*b);
		
		if (Math.abs(distance) < 4) { // any suitable constant
			Vector diffSE = Vector.subtract(centerStart, centerEnd);
			Vector diffES = Vector.subtract(centerEnd, centerStart);
			Vector diffSP = Vector.subtract(centerStart, point);
			Vector diffEP = Vector.subtract(centerEnd, point);
			
			return (Vector.scalarProduct(diffSE, diffSP) >= 0 &&
					Vector.scalarProduct(diffES, diffEP) >= 0);
		}
		
		return false;
	}

	@Override
	public void onMouseOver() {
		this.mouseOver = true;		
	}

	@Override
	public void onMouseOut() {
		this.mouseOver = false;
	}

	@Override
	public void onMouseDown() {
		// tbi
		
	}

	@Override
	public void onMouseUp() {
		// tbi
		
	}
}
