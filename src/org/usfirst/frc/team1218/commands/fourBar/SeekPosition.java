package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class SeekPosition extends Command {
	
	private double setpoint;
	final boolean  useSpecificbounds;
	
	double lowerBound = 0;
	double upperBound = 0;
	
    public SeekPosition(double setpoint) {
        requires(Robot.fourBar);
        this.setpoint = setpoint;
        useSpecificbounds = false;
    }
    
    public SeekPosition(double setpoint, double lowerBound, double upperBound){
    	requires(Robot.fourBar);
    	this.setpoint = setpoint;
    	useSpecificbounds = true;
    	this.lowerBound = lowerBound;
    	this.upperBound = upperBound;
    }
    
    protected void initialize() {
    	Robot.fourBar.dartEnablePID(true);
    	Robot.fourBar.setDartPosition(setpoint);
    	System.out.println("[Four bar]: Seeking Position: " + setpoint);
    }
    
    protected void execute() {}
    
    protected boolean isFinished() {
    	return (!useSpecificbounds && Robot.fourBar.isOnTarget()) || (useSpecificbounds && Robot.fourBar.getPosition() > lowerBound && Robot.fourBar.getPosition() < upperBound);
    }

    protected void end() {
    	Robot.fourBar.dartEnablePID(false);
    }

    protected void interrupted() {
    	end();
    }
}