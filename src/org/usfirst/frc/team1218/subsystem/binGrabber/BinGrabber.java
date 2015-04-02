package org.usfirst.frc.team1218.subsystem.binGrabber;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BinGrabber extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public static final boolean REALEASED = true;
	public static final boolean HELD  = false;
	
	Solenoid release;
	
	public BinGrabber(){
		release = new Solenoid(RobotMap.BIN_GRABBER_RELEASE);
	}
	
	public void set(boolean state) {
		release.set(state);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

