package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_GoToStepPosition extends Command {

    public C_GoToStepPosition() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	Robot.elevator.setElevatorSetpoint(Elevator.ELEVATOR_STEP_POSITION);
    	System.out.println("Elevator set to step position");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
