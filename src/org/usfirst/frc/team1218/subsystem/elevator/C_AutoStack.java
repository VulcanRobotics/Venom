package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_AutoStack extends Command {

	boolean needsToRaiseTote = false;
	
    public C_AutoStack() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
    	if (Robot.elevator.getTopLimit()) {
    		needsToRaiseTote = false;
    	}
    	
    	if (Robot.elevator.getHasTote()) {
    		
    		//if elevator has a tote on the ground, go down if its not in U, otherwise go up
    		Robot.elevator.setPower(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER * ((needsToRaiseTote) ? 1 : -1));
    			
    		if (Robot.elevator.getBottomLimit()) {
    			needsToRaiseTote = true;
    		}
    	} else {
    		//if no tote at bottom, always go up
    		Robot.elevator.setPower(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevator.setPower(0);
    }

    protected void interrupted() {
    	end();
    }
}
