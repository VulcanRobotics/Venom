package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class SeekPosition extends Command {
	
	private double setpoint;
	
    public SeekPosition(double setpoint) {
        requires(Robot.fourBar);
        this.setpoint = setpoint;
    }
    
    protected void initialize() {
    	Robot.fourBar.dartEnablePID(true);
    	Robot.fourBar.setDartPosition(setpoint);
    	System.out.println("[Four bar]: Seeking Position: " + setpoint);
    }
    
    protected void execute() {}
    
    protected boolean isFinished() {
    	return Robot.fourBar.isOnTarget();
    }

    protected void end() {
    	Robot.fourBar.dartEnablePID(false);
    }

    protected void interrupted() {
    	end();
    }
}