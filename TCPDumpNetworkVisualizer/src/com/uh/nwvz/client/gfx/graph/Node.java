package com.uh.nwvz.client.gfx.graph;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.canvas.dom.client.TextMetrics;
import com.uh.nwvz.client.gfx.CanvasEventManager;
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.Vector;

public class Node implements IGfxObject {
	// basic data
	private Vector center;
	private double radius;
	private String text;
	
	// color data
	private CssColor colorNormal;
	private CssColor colorMouseOver;
	private CssColor colorMouseDown;
	
	// event handling data
	boolean isMouseDown = false;
	boolean isMouseOver = false;
	
	List<Association> associations = new ArrayList<Association>();
	
	public Node(Vector center, double radius, String text, CssColor colorNormal, CssColor colorMouseOver, CssColor colorMouseDown) {
		this.center = center;
		this.radius = radius;
		this.text = text;
		this.colorNormal = colorNormal;
		this.colorMouseOver = colorMouseOver;
		this.colorMouseDown = colorMouseDown;
		CanvasEventManager mgr = CanvasEventManager.getCanvasEventManager();
		mgr.addMouseDownListener(this);
		mgr.addMouseUpListener(this);
		mgr.addMouseOverListener(this);
	}
	

	
	public List<Association> getAssociations() {
		return associations;
	}



	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}



	public Vector getCenter() {
		return center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setCenter(Vector center) {
		this.center = center;
	}

	public void addAssociation(Association assoc) {
		this.associations.add(assoc);
	}
	
	@Override 
	public void drawForeground(Context2d context) {
		context.setFillStyle(CssColor.make("black"));
		context.setTextAlign(TextAlign.CENTER);
		TextMetrics metrics = context.measureText(text);
		if (metrics.getWidth() < 2*radius) 
			context.fillText(text, center.getX(), center.getY() + 3 , 2*radius); 
		else if (this.isMouseOver) {
			context.fillText(text, center.getX()+2*radius, center.getY()-radius);
		}
	}
	
	@Override
	public void drawBackground(Context2d context) 
	{
		context.setFont("bold 1em Arial");
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
