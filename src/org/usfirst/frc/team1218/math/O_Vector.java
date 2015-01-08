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
    
    public O_Vector() {
        this.x = 1;
        this.y = 1;
    }
    
    public O_Vector(O_Point p1, O_Point p2) {
        x = p2.x - p1.x;
        y = p2.y - p1.y;
        //System.out.println(x);
        //System.out.println(y);
    }
    
    public void rotate90() {
        // not working now - will be special case for 90
        //trig is slow, less of it in calculating wheel positions the better
        
        //setAngle(getAngle()+90);

        double tempX = x;
        x = y;
        y = -tempX;
    }
    
    public O_Vector subtract(O_Vector subtractVector) {
        return new O_Vector(x - subtractVector.x, y - subtractVector.y);
    }
    
    public O_Vector add(O_Vector vectorToAdd) {
        return new O_Vector(x + vectorToAdd.x, y + vectorToAdd.y); 
    }
    
    public double getMagnitude() {
      return Math.sqrt(x * x + y * y);
    }
    
    public void setMagnitude(double newMagnitude) {
        double magnitude = this.getMagnitude();
        if (magnitude == 0) System.out.println("error: magnitude of vector is zero");
        double scaleFactor = newMagnitude / magnitude;
        x = x * scaleFactor;   
        y = y * scaleFactor;
    }
    
    public int getAngle() { 
       return (int)Math.toDegrees(Math.atan2(y, x));
    }
    
    public void setAngle(int angle) {
        double magnitude = this.getMagnitude();
        if (magnitude == 0) {
            System.out.println("error: magnitude of vector is zero");
            y = Math.sin(Math.toRadians(angle)) * magnitude;
            x =  Math.cos(Math.toRadians(angle)) * magnitude;
        }      
    }
}
