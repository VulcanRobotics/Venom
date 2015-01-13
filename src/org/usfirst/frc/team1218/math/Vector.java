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
    
    public void setX(double x) {
    	this.x = x;
    }
    
    public void setY(double y) {
    	this.y = y;
    }
    
    public double getX() {
    	return x;
    }
    
    public double getY() {
    	return y;
    }
    
    public double getMagnitude() {
    	return Math.sqrt(x * x + y * y);
    }
    
    /**
     * @return angle of vector
     */
    public double getAngle() {
    	double angle = Math.toDegrees(Math.atan2(x,  y));
    	return angle;
    }
    
    public void pushAngle(double angleChange) {
    	setAngle(Angle.get360Angle(angleChange + this.getAngle()));
    }
    
    public void setAngle(double angle) {
    	double magnitude = this.getMagnitude();
		x = Math.sin(Math.toRadians(angle)) * magnitude;
		y = Math.cos(Math.toRadians(angle)) * magnitude;
    }
}
