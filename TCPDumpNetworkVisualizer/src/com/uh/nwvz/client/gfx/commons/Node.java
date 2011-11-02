package com.uh.nwvz.client.gfx.commons;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.uh.nwvz.client.gfx.CanvasEventManager;

public class Node implements GfxObject {
	// basic data
	private Vector center;
	private double radius;
	
	// color data
	private CssColor colorNormal;
	private CssColor colorMouseOver;
	private CssColor colorMouseDown;
	
	// event handling data
	boolean isMouseDown = false;
	boolean isMouseOver = false;
	
	public Node(Vector center, double radius, CssColor colorNormal, CssColor colorMouseOver, CssColor colorMouseDown) {
		this.center = center;
		this.radius = radius;
		this.colorNormal = colorNormal;
		this.colorMouseOver = colorMouseOver;
		this.colorMouseDown = colorMouseDown;
		CanvasEventManager mgr = CanvasEventManager.getCanvasEventManager();
		mgr.addMouseDownListener(this);
		mgr.addMouseUpListener(this);
		mgr.addMouseOverListener(this);
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
		context.setLineJoin(LineJoin.ROUND);
		context.setLineCap(LineCap.ROUND);
		if (isMouseDown) {
			context.setFillStyle(colorMouseDown);
			context.setLineWidth(1.2);
		}
		else if (isMouseOver) {
			context.setFillStyle(colorMouseOver);
			context.setLineWidth(1.5);
		} else {
			context.setFillStyle(colorNormal);
			context.setLineWidth(1);
		}
		context.beginPath();
		context.arc(center.getX(), center.getY(), radius, 0, 2.0 * Math.PI);
		context.closePath();
		context.fill();
		context.stroke();
	}

	@Override
	public boolean isInsideObject(Vector point) {
		return (Math.sqrt(Math.pow(point.getX() - center.getX(), 2) + 
						Math.pow(point.getY() - center.getY(), 2)) <= radius);
	}

	@Override
	public void onMouseOver() {
		this.isMouseOver = true;
	}

	@Override
	public void onMouseOut() {
		this.isMouseOver = false;
		this.isMouseDown = false;
	}

	@Override
	public void onMouseDown() {
		this.isMouseDown = true;
	}

	@Override
	public void onMouseUp() {
		this.isMouseDown = false;
	}
}
