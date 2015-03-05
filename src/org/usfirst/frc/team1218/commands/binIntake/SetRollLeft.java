package org.usfirst.frc.team1218.commands.binIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetRollLeft extends Command {

	double power;
	
    public SetRollLeft(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.binIntake);
    	
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.binIntake.runLeft(power);
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
    	Robot.binIntake.runLeft(power);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
