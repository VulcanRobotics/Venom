package org.usfirst.frc.team1218.math;

/**
 *
 * @author 1218
 */
public class Vector {
    private double x;
    private double y;
    
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
    
    public void setAngle(double newAngle) {//TODO Verify
    	double magnitude = getMagnitude();
    	this.x = Math.cos(newAngle) * magnitude;
    	this.y = Math.sin(newAngle) * magnitude;
    }
    
    /**
     * Rotate vector by an offset angle
     * @param angleOffset degrees to rotate vector
     */
    public  void offsetByAngle(double angleOffset) {//TODO verify
    	double magnitude = getMagnitude();
    	this.x = Math.cos(getAngle() + angleOffset) * magnitude;
    	this.y = Math.sin(getAngle() + angleOffset) * magnitude;
    }
    
    //Magnitude Specific
    
    public double getMagnitude() {
    	return Math.sqrt(x * x + y * y);
    }
    
    public void setMagnitude(double newMagnitude) {//TODO Verify
    	double angle = getAngle();
    	this.x = Math.cos(angle) * newMagnitude;
    	this.y = Math.sin(angle) * newMagnitude;
    }
    
    public void scaleMagnitude(double scalar) {//TODO Verify
    	double angle = getAngle();
    	double magnitude = getMagnitude();
    	this.x = Math.cos(angle) * (magnitude * scalar);
    	this.y = Math.sin(angle) * (magnitude * scalar);
    }
}
