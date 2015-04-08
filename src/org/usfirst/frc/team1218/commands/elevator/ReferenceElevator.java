package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReferenceElevator extends Command {

    public ReferenceElevator() {
    	requires(Robot.elevator);
    }

    protected void initialize() {
    	System.out.println("Referencing Elevator from top limit");
    	Robot.elevator.setPower(Elevator.ELEVATOR_MIN_POSITIONING_POWER_UP);
    }

    protected void execute() {}

    protected boolean isFinished() {
    	return Robot.elevator.atEncoderReference();
    }

    protected void end() {
    	System.out.println("elevator sucessfully referenced");
    	Robot.elevator.setPower(0);
    	Robot.elevator.setEncoderPosition(Elevator.TOP_SOFT_LIMIT);
        Robot.elevator.enableSoftLimits(true);
    }

    protected void interrupted() {
    	Robot.elevator.setPower(0);
    }
}
