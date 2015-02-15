package org.usfirst.frc.team1218.subsystem.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_RunToteIntake extends Command {
	
	private double power;
	
    public C_RunToteIntake(double power) {
    	requires(Robot.toteIntake);
    	this.power = power;
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
    	Robot.toteIntake.setIntakePower(power);
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	Robot.toteIntake.setIntakePower(0.0);
    }
    
    protected void interrupted() {
    	end();
    }
}
