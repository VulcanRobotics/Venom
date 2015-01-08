package org.usfirst.frc.team1218.subsystem.swerve;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.usfirst.frc.team1218.math.MathUtils;
import org.usfirst.frc.team1218.math.O_Point;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * @author 1218
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
    
    public static void main(String[] args) {
    	
    }

    public void swerveDrive(double l, double r) {
    	double magicAngle = (1 / Math.sqrt(2)) * r;//OI.rightX();
    	double lX = l;//OI.leftX();
    	double xVector[] = {
    			(lX + magicAngle),
    			(lX - magicAngle),
    			(lX - magicAngle),
    			(lX + magicAngle)
    	};
    	
    	double yVector[] = {
    			(lX - magicAngle),
    			(lX - magicAngle),
    			(lX + magicAngle),
    			(lX + magicAngle)
    	};
    	
    	double angle[] = {
    			Math.toDegrees(Math.atan(yVector[0] / xVector[0]) - 180),
    			Math.toDegrees(Math.atan(yVector[1] / xVector[1]) - 180),
    			Math.toDegrees(Math.atan(yVector[2] / xVector[2]) - 180),
    			Math.toDegrees(Math.atan(yVector[3] / xVector[3]) - 180)
    	};
    	
    	double magnitude[] = {
    			Math.sqrt(xVector[0] * xVector[0] + yVector[0] * yVector[0]),
    			Math.sqrt(xVector[1] * xVector[1] + yVector[1] * yVector[1]),
    			Math.sqrt(xVector[2] * xVector[2] + yVector[2] * yVector[2]),
    			Math.sqrt(xVector[3] * xVector[3] + yVector[3] * yVector[3])
    	};
    	
    	double maxMagnitude = 0;
    	
    	for(int i = 0; i < 4; i++) {
    		if (magnitude[i] > maxMagnitude) maxMagnitude = magnitude[i];
    	}
    	
    	double speed[] = {
    		MathUtils.mapValues(magnitude[0], 0, maxMagnitude, -1.0, 1.0),
    		MathUtils.mapValues(magnitude[1], 0, maxMagnitude, -1.0, 1.0),
    		MathUtils.mapValues(magnitude[2], 0, maxMagnitude, -1.0, 1.0),
    		MathUtils.mapValues(magnitude[3], 0, maxMagnitude, -1.0, 1.0)
    	};
    	
    	for(int i = 0; i < 4; i++) {
    		//module[i].update(angle[i], speed[i]);
    		System.out.println("S "+speed[i] +"A: "+angle[i]);
    	}
    }
    
    /**
     * Publishes all Swerve System Values to the dashboard.
     */
    public void syncDashboard() {
    	SmartDashboard.putNumber("Wheel1Angle", module[0].angle);
        SmartDashboard.putNumber("Wheel2Angle", module[1].angle);
        SmartDashboard.putNumber("Wheel3Angle", module[2].angle);
        SmartDashboard.putNumber("Wheel4Angle", module[3].angle);
        SmartDashboard.putNumber("PIDTarget", module[3].turn.getSetpoint());
        //SmartDashboard.putNumber("GyroAngle", veerGyro.getIntAngle() % 360 - 180);
        SmartDashboard.putNumber("SM0 Encoder", module[0].turnEncoder.encoder.getRaw());
        SmartDashboard.putNumber("SM1 Encoder", module[1].turnEncoder.encoder.getRaw());
        SmartDashboard.putNumber("SM2 Encoder", module[2].turnEncoder.encoder.getRaw());
        SmartDashboard.putNumber("SM3 Encoder", module[3].turnEncoder.encoder.getRaw());
    }
}