package org.usfirst.frc.team1218.math;

/**
 *
 * @author 1218
 */
public class O_Vector {
    private double x;
    private double y;
    
    public O_Vector(double x, double y) {
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
    
    public  void offsetByAngle(double angleOffset) {
    	double magnitude = getMagnitude();
    	this.x = Math.cos(getAngle() + angleOffset) * magnitude;
    	this.y = Math.sin(getAngle() + angleOffset) * magnitude;
    }
    
    public double getMagnitude() {
    	return Math.sqrt(x * x + y * y);
    }
    
    
    
    public boolean isNegativeY() {
		return y < 0;
    }
    
    /**
     * @return angle of vector
     */
    public double getAngle() {
    	return Math.toDegrees(Math.atan(x / y));
    }
}
