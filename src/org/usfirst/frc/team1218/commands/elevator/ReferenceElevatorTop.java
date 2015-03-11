package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReferenceElevatorTop extends Command {

    public ReferenceElevatorTop() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("referencing elevator top");
    	Robot.elevator.setPower(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.elevator.getBottomLimit()) {
        	Robot.elevator.setEncoderPosition(Elevator.TOP_SOFT_LIMIT);
            Robot.elevator.enableSoftLimits(true);
            return true;
        }
    	else {
    		return false;
    	}
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
