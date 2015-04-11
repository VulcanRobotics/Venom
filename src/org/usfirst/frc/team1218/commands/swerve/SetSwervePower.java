package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetSwervePower extends Command {
	final double power;
	final double duration;
	
    public SetSwervePower(double power, double duration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    	this.power = power;
    	this.duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(duration);
    	Robot.swerveDrive.setRawWheelPower(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.swerveDrive.setRawWheelPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
