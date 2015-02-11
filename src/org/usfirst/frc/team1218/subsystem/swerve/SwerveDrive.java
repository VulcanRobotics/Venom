package org.usfirst.frc.team1218.subsystem.swerve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Provides Mid/High level module control code.
 * @author afiolmahon
 */

public class SwerveDrive extends Subsystem {
    
    protected List<SwerveModule_DIO> module;
    
    private final SerialPort navSerialPort;
    protected final IMUAdvanced navModule;
    
	private static final double X_PERPENDICULAR_CONSTANT = 0.546;
	private static final double Y_PERPENDICULAR_CONSTANT = 0.837;
	
    public SwerveDrive() {
    	module = new ArrayList<SwerveModule_DIO>(Arrays.asList(
    				new SwerveModule_DIO(0),
    				new SwerveModule_DIO(1),
    				new SwerveModule_DIO(2),
    				new SwerveModule_DIO(3)
    			));	
		navSerialPort = new SerialPort(57600, SerialPort.Port.kMXP);
		navModule = new IMUAdvanced(navSerialPort);
        System.out.println("Swerve System Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new C_Swerve());   
    }
    
    public void syncDashboard() {
    	module.stream().forEach(m -> m.syncDashboard());
    	SmartDashboard.putNumber("RobotHeading", Angle.get360Angle(navModule.getYaw()));
	}
    
    /**
     * Creates angle and power for all swerve modules
     * @param translationVector vector with magnitude <= 1
     * @param rotation a value from 1 to -1 representing the amount of rotation to add to the robot angle
     * @param fieldCentricCompensator a gyroscope output that can let the robot drive field-centric, pass 0 if robot centric drive is desired.
     */
    public void swerveDrive(Vector translationVector, double rotation, double fieldCentricCompensator) {
    	double xPerpendicular = rotation * X_PERPENDICULAR_CONSTANT;
    	double yPerpendicular = rotation * Y_PERPENDICULAR_CONSTANT;
    	
    	translationVector.pushAngle(-fieldCentricCompensator);
    	
    	List<Vector> moduleVector = new ArrayList<Vector>(Arrays.asList(
    			new Vector(translationVector.getX() + xPerpendicular, translationVector.getY() - yPerpendicular),
    			new Vector(translationVector.getX() - xPerpendicular, translationVector.getY() - yPerpendicular),
    			new Vector(translationVector.getX() - xPerpendicular, translationVector.getY() + yPerpendicular),
    			new Vector(translationVector.getX() + xPerpendicular, translationVector.getY() + yPerpendicular)
    			));
    	
    	double maxMagnitude = 0;
    	
    	for (int i = 0; i < 4; i++) maxMagnitude = (moduleVector.get(i).getMagnitude() > maxMagnitude) ? moduleVector.get(i).getMagnitude() : maxMagnitude;
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	    	
    	moduleVector.stream().forEach(v -> v.scaleMagnitude(scaleFactor));    	
    	module.stream().forEach(m -> m.setVector(moduleVector.get(m.moduleNumber)));
    }    
    
    protected List<SwerveModule_DIO> getModuleList() {
    	return module;
    }
}