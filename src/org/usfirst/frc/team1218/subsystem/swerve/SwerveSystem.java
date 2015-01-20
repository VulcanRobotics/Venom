package org.usfirst.frc.team1218.subsystem.swerve;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.subsystem.swerve.legacyModule.LegacyModule;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author afiolmahon
 */

public class SwerveSystem extends Subsystem {
    
    public List<LegacyModule> module;
    //private List<VulcanSwerveModule> module;
    
    private SerialPort navSerialPort;
    private IMUAdvanced navModule;
	private static final double WHEEL_PERPENDICULAR_CONSTANT = 1 / Math.sqrt(2);
	
    public SwerveSystem() {
    	module = new ArrayList<LegacyModule>();
    	module.add(new LegacyModule(0));
    	module.add(new LegacyModule(1));
    	module.add(new LegacyModule(2));
    	module.add(new LegacyModule(3));
    	
		navSerialPort = new SerialPort(57600, SerialPort.Port.kMXP);
		navModule = new IMUAdvanced(navSerialPort);
        System.out.println("Swerve System Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new C_Swerve());   
    }
    
    /**
     * Gyroscope reset accessor
     */
    public void resetGyro() {
    	this.navModule.zeroYaw();
    }
    
    /**
     * Write module values to dashboard
     */
    public void publishModuleValues() {
    	module.stream().forEach(m -> m.publishValues());
	}
    
    public double Module_Power = 0.5;
    
    public void toggleModulePower() {
    	double power = Module_Power;
    	if (power < 1) power += 0.1;
    	if (power > 1) power = 0.1;
    	Module_Power = power;
    }
    
    
    /**
     * Creates angle and power for all swerve modules
     */
    public void swerveDrive() {
    	double rX = WHEEL_PERPENDICULAR_CONSTANT * Math.pow(OI.getRightX(), 3);
    	Vector joystickVector = OI.getLeftJoystickVector();
    	joystickVector.pushAngle(-this.navModule.getYaw());
    	Vector vector[] = {
    			new Vector(joystickVector.getX() + rX, joystickVector.getY() - rX),
    			new Vector(joystickVector.getX() - rX, joystickVector.getY() - rX),
    			new Vector(joystickVector.getX() - rX, joystickVector.getY() + rX),
    			new Vector(joystickVector.getX() + rX, joystickVector.getY() + rX)
    	};
    	
    	double maxMagnitude = 0;
    	
    	for (int i = 0; i < 4; i++) maxMagnitude = (vector[i].getMagnitude() > maxMagnitude) ? vector[i].getMagnitude() : maxMagnitude;
    	    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	for (int i = 0; i < 4; i++) vector[i].scaleMagnitude(scaleFactor);
    	
    	module.stream().forEach(m -> m.setVector(vector[m.moduleNumber]));
    }    
}