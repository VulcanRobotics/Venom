package org.usfirst.frc.team1218.subsystem.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_AutorunToteIntake extends Command {
	
	private double power;
	
    public C_AutorunToteIntake(double power) {
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
