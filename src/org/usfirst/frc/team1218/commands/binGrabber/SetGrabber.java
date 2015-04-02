package org.usfirst.frc.team1218.commands.binGrabber;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetGrabber extends Command {
	boolean state;
    public SetGrabber(boolean state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.binGrabber);
    	this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.binGrabber.set(state);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
