package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.O_Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SS_Swerve extends Subsystem {
    
    private final O_SwerveModule[] module;

    private final Gyro gyro;
    private static double GYRO_SENSITIVITY = 0.00738888;
    
    public SS_Swerve() {
    	this.module = new O_SwerveModule[4];
    	this.module[0] = new O_SwerveModule(0, RobotMap.SM0_DRIVE_MOTOR, RobotMap.SM0_TURN_MOTOR, RobotMap.SM0_ENCODER_A, RobotMap.SM0_ENCODER_B);
    	this.module[1] = new O_SwerveModule(1, RobotMap.SM1_DRIVE_MOTOR, RobotMap.SM1_TURN_MOTOR, RobotMap.SM1_ENCODER_A, RobotMap.SM1_ENCODER_B);
    	this.module[2] = new O_SwerveModule(2, RobotMap.SM2_DRIVE_MOTOR, RobotMap.SM2_TURN_MOTOR, RobotMap.SM2_ENCODER_A, RobotMap.SM2_ENCODER_B);
    	this.module[3] = new O_SwerveModule(3, RobotMap.SM3_DRIVE_MOTOR, RobotMap.SM3_TURN_MOTOR, RobotMap.SM3_ENCODER_A, RobotMap.SM3_ENCODER_B);
    	this.gyro =  new Gyro(RobotMap.GYRO);
    	this.gyro.setSensitivity(GYRO_SENSITIVITY);
        System.out.println("Swerve System Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new C_Swerve());   
    }
    
    /**
     * Gyroscope reset accessor
     */
    public void resetGyro() {
    	this.gyro.reset();
    }
    
    /**
     * Creates angle and power for all swerve modules
     */
    public void swerveDrive() {
    	double rX = (1 / Math.sqrt(2)) * OI.getRightX();
    	double lX = OI.getLeftX();
    	double lY = OI.getLeftY();
    	
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
    		this.module[i].updateSM(fAngle, power[i]); 
    	}
    }
}