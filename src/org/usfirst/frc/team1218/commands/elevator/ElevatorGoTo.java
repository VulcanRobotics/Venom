package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorGoTo extends Command {
	double position;
	
    public ElevatorGoTo(double position) {
    	requires(Robot.elevator);
    	this.position = position;
    }

    protected void initialize() {
    	Robot.elevator.setPosition(position);
    }

    protected void execute() {}

    protected boolean isFinished() {
    	boolean atBottom = Robot.elevator.atBottom();
    	boolean atTop = Robot.elevator.atTop();
        return (atBottom && position == Elevator.BOTTOM_SOFT_LIMT) || (atTop && position == Elevator.TOP_SOFT_LIMIT);
    }

    protected void end() {
    	Robot.elevator.setPower(0);
    }

    protected void interrupted() {
    	end();
    }
}
