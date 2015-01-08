/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team1218.subsystem.swerve;

import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author liamcook
 */

//is a class so we can add a compass, 2nd gyro to read against, IMU, etc
public class O_VeerGyro extends Gyro {
    public O_VeerGyro(int channel) {
        super(channel);
        this.setSensitivity(0.00738888);
    }
    
    public int getIntAngle() {
        return (int)getAngle();
    }
    
    public double pidGet() {
        //pid needs to go from -180 to 180 to match setpoint
        return getIntAngle() - 180;
    }
    
}