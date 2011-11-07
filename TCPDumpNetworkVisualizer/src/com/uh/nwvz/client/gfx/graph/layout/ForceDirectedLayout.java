package com.uh.nwvz.client.gfx.graph.layout;

import java.util.ArrayList;
import java.util.List;

import com.uh.nwvz.client.gfx.commons.Size;
import com.uh.nwvz.client.gfx.commons.Vector;
import com.uh.nwvz.client.gfx.graph.Association;
import com.uh.nwvz.client.gfx.graph.Graph;
import com.uh.nwvz.client.gfx.graph.Node;

public class ForceDirectedLayout implements ILayout {
	private Size canvasSize = new Size(0,0);
	private Graph graph = null;
	

	@Override
	public void setGraph(Graph g) {
		this.graph = g;		
	}

	@Override
	public void setCanvasSize(Size size) {
		this.canvasSize = size;		
	}

	@Override
	public void performLayout() {
		// random initialization:
		List<NodeWrapper> nodes = new ArrayList<NodeWrapper>();
		
		int i = (int) canvasSize.getWidth() / 2 - graph.getNodes().size() / 2;
		
		for (Node n : graph.getNodes()) {
			NodeWrapper nodeWrapper = new NodeWrapper(n, new Vector(0,0));
			n.setCenter(new Vector(i,i));
			nodes.add(nodeWrapper);
			i++;
		}
		
		// now performing force-directed algorithm
		double totalKineticEnergy;
		final double damping = 0.5; // CONSTANT !!!!!
		final double timestep = 1;  // CONSTANT !!!!
		do {
			totalKineticEnergy = 0;
			
			for (int k = 0; k < nodes.size(); k++) {
				NodeWrapper node = nodes.get(k);
				
				Vector netForce = new Vector(0, 0);
				
				for (int l = 0; l < nodes.size(); l++) {
					if (l != k) {
						netForce = netForce.add(coulombRepulsion(node, nodes.get(l)));
					}
				}
				
				for (Association assoc : node.getNode().getAssociations()) {
					Node otherNode = (node.getNode() == assoc.getStartNode())?assoc.getEndNode():assoc.getStartNode();
					netForce = netForce.add(hookAttraction(node.getNode(), otherNode));
				}
				
				Vector vel = new Vector(node.getVelocity());
				vel.add(netForce.mult(timestep)).mult(damping);
				node.setVelocity(vel); 
				
				Vector pos = node.getNode().getCenter();
				pos.add(vel.mult(timestep));
		        // reference, no update
				
				totalKineticEnergy += node.getMass() * vel.norm();
			}
		} while (totalKineticEnergy > 3);		
	}
	
	private Vector coulombRepulsion(NodeWrapper startNode, NodeWrapper endNode) {
		final double gravityConstant = 3;
		
		Vector diff = Vector.subtract(startNode.getNode().getCenter(), endNode.getNode().getCenter());
		double rPow3 = Math.pow(diff.norm(), 3/2);
		return (diff.mult(gravityConstant * startNode.getMass() * endNode.getMass() / rPow3));
	}
	
	private Vector hookAttraction(Node startNode, Node endNode) {
		final double springConstant = -2;
		
		return (Vector.subtract(startNode.getCenter(), endNode.getCenter()).mult(springConstant));
	}
	
	private class NodeWrapper {
		
		private Node node = null;
		private Vector velocity = null;
		private double mass = 0;

		public NodeWrapper(Node node, Vector velocity) {
			this.node = node;
			this.velocity = velocity;
			this.mass = node.getRadius()*2; // mass derived from radius
		}
		
		public Node getNode() {
			return node;
		}
		
		public void setNode(Node node) {
			this.node = node;
		}
		
		public Vector getVelocity() {
			return velocity;
		}
		
		public void setVelocity(Vector velocity) {
			this.velocity = velocity;
		}
		
		public double getMass() {
			return mass;
		}

		public void setMass(double mass) {
			this.mass = mass;
		}
	}
}
