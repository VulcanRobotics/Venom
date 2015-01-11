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
    
    public double getMagnitude() {
    	return Math.sqrt(x * x + y * y);
    }
    
    /**
     * @return angle of vector
     */
    public double getAngle() {
    	return Math.toDegrees(Math.atan(x / y));
    }
}
