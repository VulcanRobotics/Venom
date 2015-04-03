package org.usfirst.frc.team1218.commands.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntakes extends Command {

	double left;
	double right;
	
    public SetIntakes(double left, double right) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.left = left;
    	this.right = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.toteIntake.intakeFromLeft(left);
    	Robot.toteIntake.intakeFromRight(right);
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
