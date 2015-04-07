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
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean atBottom = Robot.elevator.atBottom();
    	boolean atTop = Robot.elevator.atTop();
        return (atBottom && position == Elevator.BOTTOM_SOFT_LIMT) || (atTop && position == Elevator.TOP_SOFT_LIMIT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
