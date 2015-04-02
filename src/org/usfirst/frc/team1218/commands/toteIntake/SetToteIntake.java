package org.usfirst.frc.team1218.commands.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class SetToteIntake extends Command {
	
	private double power;
	private boolean left;
	private boolean right;
	
    public SetToteIntake(boolean left, boolean right, double power) {
    	requires(Robot.toteIntake);
    	this.power = power;
    	this.left = left;
    	this.right = right;
    }
    
    public SetToteIntake(double power) {
    	requires(Robot.toteIntake);
    	this.power = power;
    	this.left = true;
    	this.right = true;
    }
    
    protected void initialize() {
    	
    	Robot.toteIntake.intakeFromLeft((left) ? power : 0);
    	Robot.toteIntake.intakeFromRight((right) ? power : 0);
    }
    
    protected void execute() {
    	
    }
    
    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {}
    
    protected void interrupted() {}
}
