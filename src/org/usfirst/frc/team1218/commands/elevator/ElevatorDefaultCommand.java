package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class ElevatorDefaultCommand extends Command {
	
	
    public ElevatorDefaultCommand() {
        requires(Robot.elevator);
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
    	
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	
    }
    
    protected void interrupted() {
    	end();
    }
}