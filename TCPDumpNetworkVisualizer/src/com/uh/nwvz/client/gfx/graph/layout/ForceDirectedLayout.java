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
	
	private final double GRAVITY_CONSTANT = 1E-8;			// determines the force between nodes altogether
	private final double SPRING_CONSTANT = -5E-7;			// determines the force between connected nodes
	private final double MAX_KINETIC_ENERGY = 1E-5;			// constant to stop iterative process
	private final double DAMPING_CONSTANT = 0.9; 			// damping constant of the adaption process
	private final double TIMESTEP = 1;  
	

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
				
		for (Node n : graph.getNodes()) {
			NodeWrapper nodeWrapper = new NodeWrapper(n, new Vector(0,0));
			n.setCenter(new Vector(Math.random()*canvasSize.getWidth(),Math.random()*canvasSize.getHeight()));
			nodes.add(nodeWrapper);
		}
		
		// now performing force-directed algorithm
		double totalKineticEnergy;
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
				vel.add(netForce.mult(TIMESTEP)).mult(DAMPING_CONSTANT);
				node.setVelocity(vel); 
				
				Vector pos = node.getNode().getCenter();
				pos.add(vel.mult(TIMESTEP));
		        // reference, no update
				
				totalKineticEnergy += node.getMass() * vel.norm();
			}
		} while (totalKineticEnergy > MAX_KINETIC_ENERGY);		
	}
	
	private Vector coulombRepulsion(NodeWrapper startNode, NodeWrapper endNode) {		
		Vector diff = Vector.subtract(startNode.getNode().getCenter(), endNode.getNode().getCenter());
		double rPow3 = Math.pow(diff.norm(), 3/2);
		return (diff.mult(GRAVITY_CONSTANT * startNode.getMass() * endNode.getMass() / rPow3));
	}
	
	private Vector hookAttraction(Node startNode, Node endNode) {		
		return (Vector.subtract(startNode.getCenter(), endNode.getCenter()).mult(SPRING_CONSTANT));
	}
	
	private class NodeWrapper {
		
		private Node node = null;
		private Vector velocity = null;
		private double mass = 0;

		public NodeWrapper(Node node, Vector velocity) {
			this.node = node;
			this.velocity = velocity;
			this.mass = node.getRadius()*node.getRadius(); // mass proportional to area
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
