package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetHeadingController extends Command {

	final double heading;
	
    public SetHeadingController(double heading) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    	this.heading = heading;
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
    	Robot.swerveDrive.enableHeadingController(heading);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.swerveDrive.enableHeadingController(heading);
    }
}
