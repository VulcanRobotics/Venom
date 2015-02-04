package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ElevatorDefault extends Command {

	boolean hasToteInBrushes;
	
    public C_ElevatorDefault() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	hasToteInBrushes = false;
    	Robot.elevator.configureElevatorMotorControllersForPID();
    	Robot.elevator.setElevatorSetpoint(Robot.elevator.ELEVATOR_RAISE_POSITION);
    }

    protected void execute() {
    	if (Robot.elevator.getHasTote() & !hasToteInBrushes) {
    		//once a tote enters the robot and it has not already benn picked up, stop intake and lower brushes
    		Robot.elevator.setElevatorSetpoint(Robot.elevator.ELEVATOR_DROP_POSITION);
    		Robot.totePickup.setIntakePower(0.0);
    	}
    	if (Robot.elevator.getPosition() < Robot.elevator.ELEVATOR_CAPTURE_POSITION)
    	{
    		//if elevator is below tote, it has it in brushes and can now raise it
    		hasToteInBrushes = true;	
    	}
    	
    	if (hasToteInBrushes) {
    		//if the elevator has grabbed tote with brushes, it should lift it to max height
    		Robot.elevator.setElevatorSetpoint(Robot.elevator.ELEVATOR_RAISE_POSITION);
    	}
    	
    	if (hasToteInBrushes & Robot.elevator.getPosition() > Robot.elevator.ELEVATOR_CLEARNCE_POSITION) {
    		//if tote has been sucessfully picked up, allow robot to pickup another
    		hasToteInBrushes = false;
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
