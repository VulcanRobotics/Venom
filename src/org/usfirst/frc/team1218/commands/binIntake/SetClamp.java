package org.usfirst.frc.team1218.commands.binIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 *@author lcook
 */
public class SetClamp extends Command {

	boolean shouldOpen;
	
    public SetClamp(boolean shouldOpen) {
    	requires(Robot.binIntake);
    	this.shouldOpen = shouldOpen;
    }

    protected void initialize() {
    	Robot.binIntake.setClamp(shouldOpen);
    	System.out.println("Four bar claw open:" + shouldOpen);
    }

    protected void execute() {}

    protected boolean isFinished() {return true;}

    protected void end() {}
    
    protected void interrupted() {}
}
