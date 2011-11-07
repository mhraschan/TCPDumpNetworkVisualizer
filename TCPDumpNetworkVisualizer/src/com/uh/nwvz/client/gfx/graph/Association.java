package com.uh.nwvz.client.gfx.graph;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.Vector;

public class Association implements IGfxObject {
	
	private Node startNode;
	private Node endNode;
	
	public Association(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
		startNode.addAssociation(this);
		endNode.addAssociation(this);
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
	public void draw(Context2d context) {
		Vector centerStart = startNode.getCenter();
		Vector centerEnd = endNode.getCenter();
		double phi = Math.atan((centerStart.getY() - centerEnd.getY())/(centerStart.getX() - centerEnd.getX()));
		
		
		context.setLineJoin(LineJoin.ROUND);
		context.setLineCap(LineCap.ROUND);
		context.setLineWidth(1.2);
		
		context.beginPath();
		context.moveTo(centerStart.getX() + startNode.getRadius() * Math.cos(phi),
				centerStart.getY() + startNode.getRadius() * Math.sin(phi));
		context.lineTo(centerEnd.getX() - endNode.getRadius() * Math.cos(phi),
				centerEnd.getY() - endNode.getRadius() * Math.sin(phi));
		context.closePath();
		context.stroke();
		
	}

	@Override
	public boolean isInsideObject(Vector point) {
		return false;
	}

	@Override
	public void onMouseOver() {
		// TODO Auto-generated method stub	
		
	}

	@Override
	public void onMouseOut() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onMouseDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseUp() {
		// TODO Auto-generated method stub
		
	}
}
