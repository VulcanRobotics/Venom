package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EnableMaintainHeading extends Command {

	boolean shouldMaintainHeading;
	double heading;
    public EnableMaintainHeading(boolean shouldMaintainHeading, double heading) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.shouldMaintainHeading = shouldMaintainHeading;
    	this.heading = heading;
    }

    public EnableMaintainHeading(boolean shouldMaintainHeading) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.shouldMaintainHeading = shouldMaintainHeading;
    	this.heading = 0;
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveDrive.enableHeadingController(heading);
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
