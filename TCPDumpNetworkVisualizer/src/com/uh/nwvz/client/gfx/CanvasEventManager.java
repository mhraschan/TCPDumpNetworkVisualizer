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
	
	private Vector currentMousePosition = new Vector(0,0);
	
	private List<GfxObject> mouseDownListeners = new ArrayList<GfxObject>();
	private List<GfxObject> mouseUpListeners = new ArrayList<GfxObject>();
	private List<GfxObject> mouseOverListeners = new ArrayList<GfxObject>();
	
	private GfxObject lastMouseOverObject = null;
	
	private CanvasEventManager(Canvas canvas) {
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
	
	public void addMouseDownListener(GfxObject gfxObject) {
		this.mouseDownListeners.add(gfxObject);		
	}
	
	public void addMouseUpListener(GfxObject gfxObject) {
		this.mouseUpListeners.add(gfxObject);
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
		GfxObject gfxObject = findObjectAtCurrentPosition(mouseUpListeners);
		if (gfxObject != null) {
			gfxObject.onMouseUp();
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		GfxObject gfxObject = findObjectAtCurrentPosition(mouseDownListeners);
		if (gfxObject != null) {
			gfxObject.onMouseDown();
		}
		
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		currentMousePosition.setX(event.getX());
		currentMousePosition.setY(event.getY());
		
		GfxObject gfxObject = findObjectAtCurrentPosition(mouseOverListeners);
		if (gfxObject != null) {
			gfxObject.onMouseOver();
		}
		
		if (lastMouseOverObject != gfxObject) {
			if (lastMouseOverObject != null)
				lastMouseOverObject.onMouseOut();
		}
		
		lastMouseOverObject = gfxObject;
	}
	
	

}
