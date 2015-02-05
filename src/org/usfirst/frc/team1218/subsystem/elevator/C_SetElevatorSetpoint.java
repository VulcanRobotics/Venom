package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_SetElevatorSetpoint extends Command {
	
	private final int newSetpoint;
	
    public C_SetElevatorSetpoint(int newSetpoint) {
        requires(Robot.elevator);
        this.newSetpoint = newSetpoint;
    }
    
    
    protected void initialize() {
        Robot.elevator.enablePID(true);
    	Robot.elevator.setElevator(newSetpoint);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	System.out.println("Elevator position set to" + newSetpoint);
    }

    protected void interrupted() {
    }
}