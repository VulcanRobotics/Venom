package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EnableElevatorSoftLimits extends Command {

	boolean shouldEnable;
	
    public EnableElevatorSoftLimits(boolean shouldEnable) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	this.shouldEnable = shouldEnable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.enableSoftLimits(shouldEnable);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.enableSoftLimits(shouldEnable);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.enableSoftLimits(shouldEnable);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.enableSoftLimits(shouldEnable);
    }
}
