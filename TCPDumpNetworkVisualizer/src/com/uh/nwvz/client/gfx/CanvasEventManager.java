package com.uh.nwvz.client.gfx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.uh.nwvz.client.gfx.commons.GfxObject;
import com.uh.nwvz.client.gfx.commons.Vector;

public class CanvasEventManager implements MouseMoveHandler, MouseDownHandler, MouseUpHandler {	
	private static CanvasEventManager mgr = null; // singleton
	
	private Canvas canvas = null;
	
	private Vector currentMousePosition = new Vector(0,0);
	private boolean mouseDown = false;
	
	private List<GfxObject> clickListeners = new ArrayList<GfxObject>();
	private List<GfxObject> mouseOverListeners = new ArrayList<GfxObject>();
	
	private CanvasEventManager(Canvas canvas) {
		this.canvas = canvas;
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
	}
	
	public static CanvasEventManager getCanvasEventManager() {
		return mgr;
	}
	
	public static void initCanvasEventManager(Canvas canvas) {
		if (mgr == null) {
			mgr = new CanvasEventManager(canvas);
		}
	}
	
	public void addClickListener(GfxObject gfxObject) {
		this.clickListeners.add(gfxObject);		
	}
	
	public void addMouseOverListener(GfxObject gfxObject) {
		this.mouseOverListeners.add(gfxObject);		
	}
	
	private GfxObject findObjectAtCurrentPosition(List<GfxObject> gfxObjects) {
		Iterator<GfxObject> it = gfxObjects.iterator();
		GfxObject obj = null;
		while (it.hasNext()) {
			obj = it.next();
			if (obj.isInsideObject(currentMousePosition))
				return obj;
		}
		return null;
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if (this.mouseDown) { // click event
			GfxObject gfxObject = findObjectAtCurrentPosition(clickListeners);
			if (gfxObject != null) {
				gfxObject.onMouseClick();
			}
		}
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		this.mouseDown = true;
		
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		currentMousePosition.setX(event.getX());
		currentMousePosition.setY(event.getY());
		this.mouseDown = false;
		
		GfxObject gfxObject = findObjectAtCurrentPosition(mouseOverListeners);
		if (gfxObject != null) {
			gfxObject.onMouseOver();
		}
	}
	
	

}
