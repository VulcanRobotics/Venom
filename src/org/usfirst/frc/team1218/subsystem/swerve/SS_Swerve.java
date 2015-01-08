package org.usfirst.frc.team1218.subsystem.swerve;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.usfirst.frc.team1218.math.MathUtils;
import org.usfirst.frc.team1218.math.O_Point;
import org.usfirst.frc.team1218.math.O_Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author afiolmahon
 */

public class SS_Swerve extends Subsystem {
    
    O_SwerveModule[] module = new O_SwerveModule[4];

    //O_VeerGyro veerGyro = new O_VeerGyro(RobotMap.Gyro);
    
    public SS_Swerve() {
        module[0] = new O_SwerveModule(new O_Point(1,1), RobotMap.SM0_CIM, RobotMap.SM0_banebot, RobotMap.SM0_EncoderA, RobotMap.SM0_EncoderB, RobotMap.SM0_Zero, 35, true);
        module[1] = new O_SwerveModule(new O_Point(-1,1), RobotMap.SM1_CIM, RobotMap.SM1_banebot, RobotMap.SM1_EncoderA, RobotMap.SM1_EncoderB, RobotMap.SM1_Zero, -35, true);
        module[2] = new O_SwerveModule(new O_Point(-1,-1), RobotMap.SM2_CIM, RobotMap.SM2_banebot, RobotMap.SM2_EncoderA, RobotMap.SM2_EncoderB, RobotMap.SM2_Zero, -170, false);
        module[3] = new O_SwerveModule(new O_Point(1,-1), RobotMap.SM3_CIM, RobotMap.SM3_banebot, RobotMap.SM3_EncoderA, RobotMap.SM3_EncoderB, RobotMap.SM3_Zero, 100, false);
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
    	
    	double speed[] = {
    		MathUtils.mapValues(vector[0].getMagnitude(), 0, maxMagnitude, -1.0, 1.0),
    		MathUtils.mapValues(vector[1].getMagnitude(), 0, maxMagnitude, -1.0, 1.0),
    		MathUtils.mapValues(vector[2].getMagnitude(), 0, maxMagnitude, -1.0, 1.0),
    		MathUtils.mapValues(vector[3].getMagnitude(), 0, maxMagnitude, -1.0, 1.0)
    	};
    	
    	for(int i = 0; i < 4; i++) {
    		module[i].update(vector[i].getAngle(), speed[i]);
    		System.out.println("Module Set: " + i + " S: " + speed[i] + " A: " + vector[i].getAngle() + " Max Magnitude: " + maxMagnitude);
    	}
    }
    
    /**
     * Publishes all Swerve System Values to the dashboard.
     */
    public void syncDashboard() {
    	SmartDashboard.putNumber("LeftStickAngle", OI.leftAngle());
    	for(int i = 0; i < 4; i++) {
    		SmartDashboard.putNumber("SM"+i+"_Angle", module[i].angle);
    		SmartDashboard.putNumber("SM"+i+"_EncoderRaw", module[i].turnEncoder.encoder.getRaw());
    		SmartDashboard.putBoolean("SM"+i+"_isZeroing", module[i].isZeroing);
    	}
        //SmartDashboard.putNumber("GyroAngle", veerGyro.getIntAngle() % 360 - 180);
    }
}