package com.uh.nwvz.client.gfx.commons;

public class Vector {

	private double x;
	private double y;
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Vector add(Vector vec) {
		this.x += vec.getX();
		this.y += vec.getY();
		return this;
	}
	
	public Vector mult(double factor) {
		this.x *= factor;
		this.y *= factor;
		return this;
	}
	
	public double norm() {
		return (x*x + y*y);
	}
	
	public static Vector subtract(Vector v1, Vector v2) {
		double x = v1.x - v2.x;
		double y = v1.y - v2.y;
		return new Vector(x,y);
	}
	
	public static double scalarProduct(Vector v1, Vector v2) {
		return (v1.x * v2.x + v1.y * v2.y);
	}
}
