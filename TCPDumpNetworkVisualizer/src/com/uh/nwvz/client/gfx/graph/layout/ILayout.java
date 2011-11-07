package com.uh.nwvz.client.gfx.graph.layout;

import com.uh.nwvz.client.gfx.commons.Size;
import com.uh.nwvz.client.gfx.graph.Graph;

public interface ILayout {
	void setGraph(Graph g);
	
	void setCanvasSize(Size size);
	
	void performLayout();
}