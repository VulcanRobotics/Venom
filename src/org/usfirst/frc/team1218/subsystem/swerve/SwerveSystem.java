package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SwerveSystem extends Subsystem {
    
    public SwerveModule[] module;

    private final Gyro gyro;
    private static double GYRO_SENSITIVITY = 0.00738888;
    
    public SwerveSystem() {
    	module = new SwerveModule[4];
    	for (int i = 0; i < 4; i++) module[i] = new SwerveModule(i);
    	
    	gyro =  new Gyro(RobotMap.GYRO);
    	gyro.setSensitivity(GYRO_SENSITIVITY);
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
     * Write module set and sensor values to dashboard
     */
    public void publishModuleValues() {
		for (int i = 0; i < 4; i++) module[i].publishValues();
	}
    
    /**
     * Creates angle and power for all swerve modules
     */
    public void swerveDrive() {
    	double rX = (1 / Math.sqrt(2)) * Math.pow(OI.getRightX(), 3);
    	Vector joystickVector = OI.getLeftJoystickVector();
    	joystickVector.pushAngle(-gyro.getAngle());
    	Vector vector[] = {
    			new Vector(joystickVector.getX() + rX, joystickVector.getY()  - rX),
    			new Vector(joystickVector.getX() - rX, joystickVector.getY()  - rX),
    			new Vector(joystickVector.getX() - rX, joystickVector.getY()  + rX),
    			new Vector(joystickVector.getX()  + rX, joystickVector.getY()  + rX)
    	};
    	
    	double maxMagnitude = 0;
    	
    	for(int i = 0; i < 4; i++) {
    		maxMagnitude = (vector[i].getMagnitude() > maxMagnitude) ? vector[i].getMagnitude() : maxMagnitude;
    	}
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	for (int i = 0; i < 4; i++) {
    		vector[i].scaleMagnitude(scaleFactor);
    		module[i].setVector(vector[i]);
    	}    	
    }
}