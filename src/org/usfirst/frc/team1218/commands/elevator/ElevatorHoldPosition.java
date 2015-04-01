package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorHoldPosition extends Command {

	double position;
	
    public ElevatorHoldPosition(double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	if (position > Elevator.TOP_SOFT_LIMIT || position < Elevator.BOTTOM_SOFT_LIMT) {
    		System.out.println("tried to assign an elevator position that was out of bounds");
    		position = 1000;
    	}
    	else {
    		this.position = position;
    	}
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.setPosition(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
