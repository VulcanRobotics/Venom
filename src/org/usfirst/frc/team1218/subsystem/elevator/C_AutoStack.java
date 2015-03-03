package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_AutoStack extends Command {

	boolean needsToRaiseTote = false;
	
    public C_AutoStack() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.elevator.getTopLimit()) {
    		needsToRaiseTote = false;
    	}
    	if(Robot.elevator.getHasTote()) {
    		
    		//if elevator has a tote on the ground, go down if its not in U, otherwise go up
    		if (needsToRaiseTote) {
    			Robot.elevator.setPower(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    		}
    		else {
    			Robot.elevator.setPower(-Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    		}
    			
    		if (Robot.elevator.getBottomLimit()) {
    			needsToRaiseTote = true;
    		}
    	}
    	else {
    		//if no tote at bottom, always go up
    		Robot.elevator.setPower(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    	}
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
