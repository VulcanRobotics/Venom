/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team1218.math;

/**
 * Angle class, will add more functionality / merge directly into the swerve module class later.
 * @author afiolmahon
 */
public class O_Angle {
    private int angle;
    
    public O_Angle(int angle) {
        this.angle = angle;
    }
    
    public void set(int angle) {
        this.angle = angle % 360;
    }
    
    public void push(int delta) {
        this.angle = (this.angle + delta) % 360;
    }
    
    public int get() {
        return angle;
    }
}
