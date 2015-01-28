package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_RaiseStack extends Command {

    public C_RaiseStack() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	Robot.elevator.setElevatorSetpoint(Elevator.ELEVATOR_RAISE_POSITION);
    	System.out.println("Elevator set to Raised position");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
