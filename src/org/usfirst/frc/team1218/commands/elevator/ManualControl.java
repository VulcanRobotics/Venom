package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class ManualControl extends Command {
	
	
    public ManualControl() {
        requires(Robot.elevator);
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
    	if (OI.elevatorManualRaise.get()) {
        	Robot.elevator.setPosition(Elevator.TOP_SOFT_LIMIT);
    	} else if (OI.elevatorManualLower.get()) {
        	Robot.elevator.setPosition(Elevator.BOTTOM_SOFT_LIMT);
    	} else {
    		Robot.elevator.setPower(0.0);
    	}
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	
    }
    
    protected void interrupted() {
    	end();
    }
}