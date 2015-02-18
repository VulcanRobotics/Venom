package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_GoToBottom extends Command {

    public C_GoToBottom() {
         requires(Robot.elevator);
    }

    protected void initialize() {
    	System.out.println("Elevator going to bottom");
    	Robot.elevator.setPower(-Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.elevator.getBottomLimit();
    }

    protected void end() {
    	System.out.println("Elevator at Bottom");
    	Robot.elevator.setPower(0);
    }

    protected void interrupted() {
    	end();
    }
}
