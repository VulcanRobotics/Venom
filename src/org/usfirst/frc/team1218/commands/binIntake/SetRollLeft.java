package org.usfirst.frc.team1218.commands.binIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiolmahon
 * @author lcook
 */
public class SetRollLeft extends Command {
	
	double power;
	
    public SetRollLeft(double power) {
    	requires(Robot.binIntake);
    	this.power = power;
    }
    
    protected void initialize() {
    	Robot.binIntake.setBinIntake(power);
    }
    
    protected void execute() {}
    
    protected boolean isFinished() {return true;}
    
    protected void end() {}
    
    protected void interrupted() {
    	end();
    }
}
