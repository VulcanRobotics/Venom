package org.usfirst.frc.team1218.subsystem.swerve.math;

/**
 *
 * @author 1218
 * @author afiolmahon
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
    
    public void add(Vector newVector){
    	this.x += newVector.x;
    	this.y += newVector.y;
    }
    
    public void setMagnitude(double newMagnitude) {
    	double oldMagnitude = this.getMagnitude();
    	this.setX(getX() / oldMagnitude * newMagnitude);
    	this.setY(getY() / oldMagnitude * newMagnitude);
    }
    
    public void scaleMagnitude(double scalar) {
    	setMagnitude(this.getMagnitude() * scalar);
    }
    
    /**
     * @return angle of vector
     */
    public double getAngle() {
    	double angle = Math.toDegrees(Math.atan2(x,  y));
    	return angle;
    }
    
    public void pushAngle(double angleChange) {
    	setAngle(angleChange + this.getAngle());
    }
    
    public void setAngle(double angle) {
    	double magnitude = this.getMagnitude();
		x = Math.sin(Math.toRadians(angle)) * magnitude;
		y = Math.cos(Math.toRadians(angle)) * magnitude;
    }
    
    /**
     * Print all properties of angle for debug
     * @return
     */
    public String debug(String label) {
    	String out = "Vector[ " + label + " ]: X[ " + getX() + " ] Y[ " + getY() + " ] Angle[ " + getAngle() + " ] Magnitude[ " + getMagnitude() + " ]";
		return out;
    }
}
