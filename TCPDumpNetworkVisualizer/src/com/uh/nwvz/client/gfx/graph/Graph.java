package com.uh.nwvz.client.gfx.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private List<Node> nodes = null;
	private List<Association> associations = null;
	
	public Graph() {
		nodes = new ArrayList<Node>();
		associations = new ArrayList<Association>();
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void addAssociation(Association a) {
		associations.add(a);
	}
}
