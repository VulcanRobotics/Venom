package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.command.Swerve;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SS_SwerveSystem extends Subsystem {
    
    public SwerveModule[] module;//XXX module array should be private

    private final Gyro gyro;
    private static double GYRO_SENSITIVITY = 0.00738888;
    
    public SS_SwerveSystem() {
    	module = new SwerveModule[4];
    	for (int i = 0; i < 4; i++) module[i] = new SwerveModule(i);

    	this.gyro =  new Gyro(RobotMap.GYRO);
    	this.gyro.setSensitivity(GYRO_SENSITIVITY);
        System.out.println("Swerve System Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new Swerve());   
    }
    
    /**
     * Resets the gyro.
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
     * Writes a vector to each swerve drive
     */
    public void swerveDrive() {
    	//Read Inputs
    	double turnVector = (1 / Math.sqrt(2)) * OI.getRightJoystickVector().getX();
    	Vector joystickVector = OI.getLeftJoystickVector();
    	//joystickVector.offsetByAngle(-gyro.getAngle());//FIXME This code doesn't work for some reason
    	
    	//Combine Inputs
    	Vector vector[] = {
    			new Vector(joystickVector.getX() + turnVector, joystickVector.getY()  - turnVector),
    			new Vector(joystickVector.getX() - turnVector, joystickVector.getY()  - turnVector),
    			new Vector(joystickVector.getX() - turnVector, joystickVector.getY()  + turnVector),
    			new Vector(joystickVector.getX() + turnVector, joystickVector.getY()  + turnVector)
    	};
    	
    	//Scaling and Compensation
    	double maxMagnitude = 0;
    	for (int i = 0; i < 4; i++) if (vector[i].getMagnitude() > maxMagnitude) maxMagnitude = vector[i].getMagnitude();
    	
    	double magnitudeScalar = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	for (int i = 0; i < 4; i++) {
    		vector[i].scaleMagnitude(magnitudeScalar);
    		if (vector[i].getY() < 0) vector[i].offsetByAngle(180);//TODO verify offsetByAngle method
    	}
    	
    	//Write Values
    	for (int i = 0; i < 4; i++) module[i].setVector(vector[i]);
    }
}