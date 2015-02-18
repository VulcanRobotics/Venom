package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_GoToTop extends Command {

    public C_GoToTop() {
    	requires(Robot.elevator);
    }

    protected void initialize() {
    	System.out.println("Elevator going to top");
    	Robot.elevator.setPower(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.elevator.getTopLimit();
    }

    protected void end() {
    	System.out.println("Eleavtor at top");
    	Robot.elevator.setPower(0);
    }

    protected void interrupted() {
    	end();
    }
}
