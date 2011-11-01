package com.uh.nwvz.client.gfx.commons;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class Node implements GfxObject {
	
	private Vector center;
	private CssColor color;
	private double radius;
	
	public Node(Vector center, double radius, CssColor color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
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
	}
}
