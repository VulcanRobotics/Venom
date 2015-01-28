package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_SetElevatorPosition extends Command {
	
	private final int newSetpoint;
	
    public C_SetElevatorPosition(int newSetpoint) {
        requires(Robot.elevator);
        this.newSetpoint = newSetpoint;
    }
    
    
    protected void initialize() {
    	Robot.elevator.setElevatorSetpoint(newSetpoint);
    	System.out.println("Elevator position set to" + newSetpoint);
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