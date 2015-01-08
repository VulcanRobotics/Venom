package org.usfirst.frc.team1218.math;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
       return Math.toDegrees(Math.atan(y / x));// - 180;
    }
}
