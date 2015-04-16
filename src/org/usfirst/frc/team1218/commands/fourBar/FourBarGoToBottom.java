package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FourBarGoToBottom extends Command {

    public FourBarGoToBottom() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.fourBar);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.fourBar.setDartPower(-0.8);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.fourBar.pidGet() < 0.05;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.fourBar.setDartPosition(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
