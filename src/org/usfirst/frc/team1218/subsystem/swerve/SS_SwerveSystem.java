package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SS_SwerveSystem extends Subsystem {
    
    protected SwerveModule[] module;

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
    	double rX = (1 / Math.sqrt(2)) * OI.getRightJoystickVector().getX();
    	Vector translationVector = OI.getLeftJoystickVector();
    	System.out.println(translationVector.getAngle());
    	translationVector.pushAngle(-gyro.getAngle());//Makes Robot field Centric
    	
    	Vector vector[] = {
    			new Vector(translationVector.getX() + rX, translationVector.getY()  - rX),
    			new Vector(translationVector.getX() - rX, translationVector.getY()  - rX),
    			new Vector(translationVector.getX() - rX, translationVector.getY()  + rX),
    			new Vector(translationVector.getX()  + rX, translationVector.getY()  + rX)
    	};
    	
    	double maxMagnitude = 0;
    	
    	for(int i = 0; i < 4; i++) {
    		if (vector[i].getMagnitude() > maxMagnitude) maxMagnitude = vector[i].getMagnitude();
    	}
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	for (int i = 0; i < 4; i++) vector[i].scaleMagnitude(scaleFactor);
    	
    	for(int i = 0; i < 4; i++) module[i].setVector(vector[i]);
    }
}