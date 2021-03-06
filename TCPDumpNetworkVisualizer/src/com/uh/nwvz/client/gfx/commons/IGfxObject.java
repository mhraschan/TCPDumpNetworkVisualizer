package com.uh.nwvz.client.gfx.commons;

import com.google.gwt.canvas.dom.client.Context2d;

public interface IGfxObject {
	
	void drawBackground(Context2d context);
	
	void drawForeground(Context2d context);
	
	boolean isInsideObject(Vector point);
		
	void onMouseOver();
	
	void onMouseOut();
	
	void onMouseDown();
	
	void onMouseUp();
	
	void unregisterEventListeners();
}
