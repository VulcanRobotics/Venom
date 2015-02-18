package org.usfirst.frc.team1218.subsystem.binIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_SetClaw extends Command {

	boolean shouldOpen;
	
    public C_SetClaw(boolean shouldOpen) {
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
