package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.O_Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SS_SwerveSystem extends Subsystem {
    
    public SwerveModule[] module;

    private final Gyro gyro;
    private static double GYRO_SENSITIVITY = 0.00738888;
    
    public SS_SwerveSystem() {
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
		for (int i = 0; i < 4; i++) {
			module[i].publishValues();
		}
	}
    
    /**
     * Creates angle and power for all swerve modules
     */
    public void swerveDrive() {
    	double rX = (1 / Math.sqrt(2)) * OI.getRightX();
    	O_Vector joystickVector = new O_Vector(OI.getLeftX(), OI.getLeftY());
    	//joystickVector.offsetByAngle( - (gyro.getAngle() % 360));
    	    	
    	O_Vector vector[] = {
    			new O_Vector(joystickVector.getX() + rX, joystickVector.getY()  - rX),
    			new O_Vector(joystickVector.getX() - rX, joystickVector.getY()  - rX),
    			new O_Vector(joystickVector.getX() - rX, joystickVector.getY()  + rX),
    			new O_Vector(joystickVector.getX()  + rX, joystickVector.getY()  + rX)
    	};
    	
    	double maxMagnitude = 0;
    	
    	for(int i = 0; i < 4; i++) {
    		if (vector[i].getMagnitude() > maxMagnitude) maxMagnitude = vector[i].getMagnitude();
    	}
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	double power[] = {
    		vector[0].getMagnitude() * scaleFactor,
    		vector[1].getMagnitude() * scaleFactor,
    		vector[2].getMagnitude() * scaleFactor,
    		vector[3].getMagnitude() * scaleFactor
    	};
    	
    	for(int i = 0; i < 4; i++) {
    		double mAngle = vector[i].getAngle();
    		mAngle = (vector[i].getY() < 0) ? (mAngle + 180) % 360 : mAngle; //Invert angle if wheel power should be negative;
    		module[i].setValues(mAngle, power[i]);
    	}
    }
}