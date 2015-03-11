package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReferenceElevatorTop extends Command {

    public ReferenceElevatorTop() {
    	requires(Robot.elevator);
    }

    protected void initialize() {
    	System.out.println("Referencing Elevator from top limit");
    	Robot.elevator.setPower(Elevator.ELEVATOR_POSITIONING_POWER);
    }

    protected void execute() {
    	
    }

    protected boolean isFinished() {
    	return Robot.elevator.atEncoderReference();
    }

    protected void end() {
    	Robot.elevator.setPower(0);
    	Robot.elevator.setEncoderPosition(Elevator.TOP_SOFT_LIMIT);
        Robot.elevator.enableSoftLimits(true);
    }

    protected void interrupted() {
    	Robot.elevator.setPower(0);
    }
}
