package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_SeekPosition extends Command {
	
	private double setpoint;
	
    public C_SeekPosition(double setpoint) {
        requires(Robot.fourBar);
        this.setpoint = setpoint;
    }
    
    protected void initialize() {
    	Robot.fourBar.enableDarts();
    	Robot.fourBar.setDartPosition(setpoint);
    	System.out.println("[Escalator]: Seeking Position...");
    }
    
    protected void execute() {
    }
    
    protected boolean isFinished() {
        //Relies on OI Button to end command when released
    	return false;
    }

    protected void end() {
    	Robot.fourBar.setDartPower(0.0);
    }

    protected void interrupted() {
    	end();
    }
}