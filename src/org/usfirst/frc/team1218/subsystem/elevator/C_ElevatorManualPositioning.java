package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ElevatorManualPositioning extends Command {
	
	private final double power;
	
    public C_ElevatorManualPositioning(double power) {
    	requires(Robot.elevator);
    	this.power = power;
    }
    
    protected void initialize() {}
    
    protected void execute() {
    	Robot.elevator.setPower(power);
    }
    
    protected boolean isFinished() {
    	//Ended by OI
        return false;
    }
    
    protected void end() {
    	Robot.elevator.setPosition(Robot.elevator.getPosition());
    }
    
    protected void interrupted() {
    	end();
    }
}
