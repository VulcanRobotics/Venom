package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoToPosition extends Command {

	final double UP_POWER = 0.6;
	final double DOWN_POWER = -0.3;
	
	final double minBound;
	final double maxBound;
	
    public GoToPosition(double minBound, double maxBound) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.fourBar);
    	this.minBound = minBound;
    	this.maxBound = maxBound;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double power;
    	if (Robot.fourBar.getPosition() > maxBound){
    		power = UP_POWER;
    	}
    	else {
    		power = DOWN_POWER;
    	}
    	Robot.fourBar.setDartPower(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.fourBar.getPosition() > minBound || Robot.fourBar.getPosition() < maxBound;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.fourBar.setDartPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
