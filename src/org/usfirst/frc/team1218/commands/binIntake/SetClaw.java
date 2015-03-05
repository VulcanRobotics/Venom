package org.usfirst.frc.team1218.commands.binIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetClaw extends Command {

	boolean shouldOpen;
	
    public SetClaw(boolean shouldOpen) {
    	requires(Robot.binIntake);
    	this.shouldOpen = shouldOpen;
    }

    protected void initialize() {
    	Robot.binIntake.setClamp(shouldOpen);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	System.out.println("Four bar claw open:" + shouldOpen);
    }
    
    protected void interrupted() {
    	end();
    }
}
