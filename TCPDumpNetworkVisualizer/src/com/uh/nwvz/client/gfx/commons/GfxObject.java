package com.uh.nwvz.client.gfx.commons;

import com.google.gwt.canvas.dom.client.Context2d;

public interface GfxObject {
	
	void draw(Context2d context);
	
	boolean isInsideObject(Vector point);
	
	void onMouseClick();
	
	void onMouseOver();
}
