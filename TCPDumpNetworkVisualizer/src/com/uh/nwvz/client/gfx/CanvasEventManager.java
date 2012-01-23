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
import com.uh.nwvz.client.gfx.commons.IGfxObject;
import com.uh.nwvz.client.gfx.commons.Vector;

public class CanvasEventManager implements MouseMoveHandler, MouseDownHandler, MouseUpHandler {	
	private static CanvasEventManager mgr = null; // singleton
	
	private Vector currentMousePosition = new Vector(0,0);
	
	private List<IGfxObject> mouseDownListeners = new ArrayList<IGfxObject>();
	private List<IGfxObject> mouseUpListeners = new ArrayList<IGfxObject>();
	private List<IGfxObject> mouseOverListeners = new ArrayList<IGfxObject>();
	
	private IGfxObject lastMouseOverObject = null;
	
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
	
	public void removeMouseDownListener(IGfxObject gfxObject) {
		this.mouseDownListeners.remove(gfxObject);
	}
	
	public void removeMouseUpListener(IGfxObject gfxObject) {
		this.mouseUpListeners.remove(gfxObject);
	}
	
	public void removeMouseOverListener(IGfxObject gfxObject) {
		this.mouseOverListeners.remove(gfxObject);
	}
	
	public void addMouseDownListener(IGfxObject gfxObject) {
		this.mouseDownListeners.add(gfxObject);		
	}
	
	public void addMouseUpListener(IGfxObject gfxObject) {
		this.mouseUpListeners.add(gfxObject);
	}
	
	public void addMouseOverListener(IGfxObject gfxObject) {
		this.mouseOverListeners.add(gfxObject);		
	}
	
	private IGfxObject findObjectAtCurrentPosition(List<IGfxObject> gfxObjects) {
		Iterator<IGfxObject> it = gfxObjects.iterator();
		IGfxObject obj = null;
		while (it.hasNext()) {
			obj = it.next();
			if (obj.isInsideObject(currentMousePosition))
				return obj;
		}
		return null;
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		IGfxObject gfxObject = findObjectAtCurrentPosition(mouseUpListeners);
		if (gfxObject != null) {
			gfxObject.onMouseUp();
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		IGfxObject gfxObject = findObjectAtCurrentPosition(mouseDownListeners);
		if (gfxObject != null) {
			gfxObject.onMouseDown();
		}
		
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		currentMousePosition.setX(event.getX());
		currentMousePosition.setY(event.getY());
		
		IGfxObject gfxObject = findObjectAtCurrentPosition(mouseOverListeners);
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
