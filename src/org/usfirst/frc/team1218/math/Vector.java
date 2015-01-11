package org.usfirst.frc.team1218.math;

/**
 * @author afiolmahon
 */
public class Vector {
    private double x;
    private double y;
    
    /**
     * 
     * @param x component of vector
     * @param y component of vector
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    //Coordinate Specific
    
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
    
    //Angle Specific
    
    public double getAngle() {
    	return Math.toDegrees(Math.atan(x / y));
    }
    
    public void setAngle(double newAngle) {//FIXME broken math
    	newAngle = -newAngle;
    	if (Math.abs(newAngle) > 180) {
    		
    	}
    	double currentX = getX();
    	double currentY = getY();
    	//this.x = Math.cos(Math.toRadians(newAngle)) * magnitude;
    	//this.y = Math.sin(Math.toRadians(newAngle)) * magnitude;
    	x = currentX * Math.cos(Math.toRadians(newAngle)) - currentY * Math.sin(Math.toRadians(newAngle));
        y = currentX * Math.sin(Math.toRadians(newAngle)) + currentY * Math.cos(Math.toRadians(newAngle));
    }
    
    /**
     * Rotate vector by an offset angle
     * @param angleOffset degrees to rotate vector
     */
    public  void offsetByAngle(double offsetAngle) {
    	setAngle(getAngle() + offsetAngle);
    }
    
    //Magnitude Specific
    
    public double getMagnitude() {
    	return Math.sqrt(x * x + y * y);
    }
    
    public void setMagnitude(double newMagnitude) {
    	double angle = getAngle();
    	this.x = Math.cos(Math.toRadians(angle)) * newMagnitude;
    	this.y = Math.sin(Math.toRadians(angle)) * newMagnitude;
    }
    
    public void scaleMagnitude(double scalar) {
    	double angle = getAngle();
    	double magnitude = getMagnitude();
    	this.x = Math.cos(Math.toRadians(angle)) * (magnitude * scalar);
    	this.y = Math.sin(Math.toRadians(angle)) * (magnitude * scalar);
    }
    
    /**
     * For testing vector math.
     * @param args
     */
    public static void main(String[] args) {
    	Vector vector = new Vector(0,1);
    	System.out.println("Initial vector: " + vector.getX() + ", " + vector.getY());
    	System.out.println("Initial Magnitude: " + vector.getMagnitude());
    	System.out.println("Initial angle: " + vector.getAngle());
    	
    	vector.setAngle(179);
    	
    	System.out.println("Return Vector: " + vector.getX() + ", " + vector.getY());
    	System.out.println("Return Magnitude: " + vector.getMagnitude());
    	System.out.println("Return angle: " + vector.getAngle());
    }
}
