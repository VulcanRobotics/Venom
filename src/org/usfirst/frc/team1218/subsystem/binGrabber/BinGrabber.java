package org.usfirst.frc.team1218.subsystem.binGrabber;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Auton Can Grabber subsystem
 * @author lcook
 */
public class BinGrabber extends Subsystem {
	
	public static final boolean REALEASED = true;
	public static final boolean HELD = false;
	
	private Solenoid release;
	
	public BinGrabber() {
		release = new Solenoid(RobotMap.BIN_GRABBER_RELEASE);
	}
	
	public void set(boolean state) {
		release.set(state);
	}
	
	public boolean get() {
		return release.get();
	}
	
    public void initDefaultCommand() {
    }
}

