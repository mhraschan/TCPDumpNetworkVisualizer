package com.uh.nwvz.client.gfx.commons;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.CanvasEventManager;

public class Node implements GfxObject {
	
	private Vector center;
	private CssColor color;
	private double radius;
	boolean stroke = false;
	
	public Node(Vector center, double radius, CssColor color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		CanvasEventManager mgr = CanvasEventManager.getCanvasEventManager();
		mgr.addClickListener(this);
	}
	
	public Vector getCenter() {
		return center;
	}
	
	public double getRadius() {
		return radius;
	}

	@Override
	public void draw(Context2d context) 
	{
		context.setFillStyle(color);
		context.beginPath();
		context.arc(center.getX(), center.getY(), radius, 0, 2.0 * Math.PI);
		context.closePath();
		context.fill();
		if (stroke)
			context.stroke();
	}

	@Override
	public boolean isInsideObject(Vector point) {
		return (Math.sqrt(Math.pow(point.getX() - center.getX(), 2) + 
						Math.pow(point.getY() - center.getY(), 2)) <= radius);
	}

	@Override
	public void onMouseClick() {
		stroke = !stroke;
		
	}

	@Override
	public void onMouseOver() {
		// TODO Auto-generated method stub
		
	}
}
