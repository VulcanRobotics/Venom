package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ElevatorManualPositioning extends Command {
	
	private final double power;
	
    public C_ElevatorManualPositioning(double power) {
    	requires(Robot.elevator);
    	this.power = power;
    }
    
    protected void initialize() {
    	Robot.elevator.enablePID(false);
    	Robot.elevator.setElevator(power);
    }
    
    protected void execute() {
    }
    
    protected boolean isFinished() {
    	//Ended by OI
        return false;
    }
    
    protected void end() {
    	Robot.elevator.enablePID(true);
    	Robot.elevator.setElevator(Robot.elevator.getPosition());
    }
    
    protected void interrupted() {
    	end();
    }
}
