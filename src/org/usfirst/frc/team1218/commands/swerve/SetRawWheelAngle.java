package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetRawWheelAngle extends Command {

	final double angle;
	
    public SetRawWheelAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerveDrive);
    	this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveDrive.setRawWheelAngle(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.swerveDrive.isAnglePIDOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
