package org.usfirst.frc.team1218.math;

/**
 *
 * @author 1218
 */
public class O_Vector {
    public double x;
    public double y;
    
    public O_Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getMagnitude() {
      return Math.sqrt(x * x + y * y);
    }
    
    /**
     * 
     * @return A degree from -180 to 180
     */
    public double getAngle() { 
       return Math.toDegrees(Math.atan(x / y));
    }
}
