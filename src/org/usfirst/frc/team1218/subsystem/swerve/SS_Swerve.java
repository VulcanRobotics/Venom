package org.usfirst.frc.team1218.subsystem.swerve;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.usfirst.frc.team1218.math.O_Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SS_Swerve extends Subsystem {
    
    public O_SwerveModule[] module = new O_SwerveModule[4];

    O_VeerGyro veerGyro = new O_VeerGyro(RobotMap.GYRO);
    
    public SS_Swerve() {
        module[0] = new O_SwerveModule(RobotMap.SM0_DRIVE_MOTOR, RobotMap.SM0_TURN_MOTOR, RobotMap.SM0_ENCODER_A, RobotMap.SM0_ENCODER_B);
        module[1] = new O_SwerveModule(RobotMap.SM1_DRIVE_MOTOR, RobotMap.SM1_TURN_MOTOR, RobotMap.SM1_ENCODER_A, RobotMap.SM1_ENCODER_B);
        module[2] = new O_SwerveModule(RobotMap.SM2_DRIVE_MOTOR, RobotMap.SM2_TURN_MOTOR, RobotMap.SM2_ENCODER_A, RobotMap.SM2_ENCODER_B);
        module[3] = new O_SwerveModule(RobotMap.SM3_DRIVE_MOTOR, RobotMap.SM3_TURN_MOTOR, RobotMap.SM3_ENCODER_A, RobotMap.SM3_ENCODER_B);
        System.out.println("Swerve Modules Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new C_Swerve());   
    }
    
    public void swerveDrive() {
    	double rX = (1 / Math.sqrt(2)) * OI.rightX();
    	double lX = OI.leftX();
    	double lY = OI.leftY();
    	
    	O_Vector vector[] = {
    			new O_Vector(lX + rX, lY - rX),
    			new O_Vector(lX - rX, lY - rX),
    			new O_Vector(lX - rX, lY + rX),
    			new O_Vector(lX + rX, lY + rX)
    	};
    	
    	double maxMagnitude = 0;
    	
    	for(int i = 0; i < 4; i++) {
    		if (vector[i].getMagnitude() > maxMagnitude) maxMagnitude = vector[i].getMagnitude();
    	}
    	
    	double scaleFactor = (maxMagnitude > 1.0) ? 1.0/maxMagnitude : 1.0;
    	
    	double power[] = {
    		vector[0].getMagnitude() * scaleFactor,
    		vector[1].getMagnitude() * scaleFactor,
    		vector[2].getMagnitude() * scaleFactor,
    		vector[3].getMagnitude() * scaleFactor
    	};
    	for(int i = 0; i < 4; i++) {//FIXME compensate for other SM math (all use module 0 math)
    		double fAngle = (vector[i].getAngle() < 0) ? 360-Math.abs(vector[i].getAngle()): vector[i].getAngle();
        	module[i].updateSM(fAngle, power[i]); 
    	}
    }
}