package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_SetElevatorSetpoint extends Command {
	
	private final int position;
	
    public C_SetElevatorSetpoint(int position) {
        requires(Robot.elevator);
        this.position = position;
    }
    
    
    protected void initialize() {
    	Robot.elevator.setPosition(position);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	System.out.println("Elevator position set to " + position);
    }

    protected void interrupted() {
    }
}