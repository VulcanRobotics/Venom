package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorHoldPositionWhenToteDetected extends Command {

	double position;
	
    public ElevatorHoldPositionWhenToteDetected(double position) {
    	if (position > Elevator.TOP_SOFT_LIMIT || position < Elevator.BOTTOM_SOFT_LIMT) {
    		System.out.println("tried to assign an elevator position that was out of bounds");
    		position = 1000;
    	} else {
    		this.position = position;
    	}
    }

    protected void initialize() {}

    protected void execute() {
    	if (Robot.elevator.hasTote()){
    		Robot.elevator.setPosition(position);
    	}
    	
    }
    
    protected boolean isFinished() {return false;}

    protected void end() {
    	Robot.elevator.setPower(0);
    }

    protected void interrupted() {
    	end();
    }
}
