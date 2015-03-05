package org.usfirst.frc.team1218.commands.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class AutorunToteIntake extends Command {
	
	private double power;
	
    public AutorunToteIntake(double power) {
    	requires(Robot.toteIntake);
    	this.power = power;
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
    	Robot.toteIntake.setIntakePower(power);
    }
    
    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {
    	
    }
    
    protected void interrupted() {
    	end();
    }
}
