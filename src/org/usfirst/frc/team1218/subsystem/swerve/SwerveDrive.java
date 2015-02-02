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
    
    private List<SwerveModule_Digital> module;
    
    private final SerialPort navSerialPort;
    protected final IMUAdvanced navModule;
    
	private static final double WHEEL_PERPENDICULAR_CONSTANT = 1 / Math.sqrt(2);//FIXME Update Constant
	
    public SwerveDrive() {
    	module = new ArrayList<SwerveModule_Digital>(Arrays.asList(
    				new SwerveModule_Digital(0),
    				new SwerveModule_Digital(1),
    				new SwerveModule_Digital(2),
    				new SwerveModule_Digital(3)
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
    	rotation *= WHEEL_PERPENDICULAR_CONSTANT;
    	translationVector.pushAngle(-fieldCentricCompensator);
    	Vector vector[] = {
    			new Vector(translationVector.getX() + rotation, translationVector.getY() - rotation),
    			new Vector(translationVector.getX() - rotation, translationVector.getY() - rotation),
    			new Vector(translationVector.getX() - rotation, translationVector.getY() + rotation),
    			new Vector(translationVector.getX() + rotation, translationVector.getY() + rotation)
    	};
    	
    	double maxMagnitude = 0;
    	
    	for (int i = 0; i < 4; i++) maxMagnitude = (vector[i].getMagnitude() > maxMagnitude) ? vector[i].getMagnitude() : maxMagnitude;
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	for (int i = 0; i < 4; i++) vector[i].scaleMagnitude(scaleFactor);
    	
    	module.stream().forEach(m -> m.setVector(vector[m.moduleNumber]));
    }    
    
    protected List<SwerveModule_Digital> getModuleList() {
    	return module;
    }
}