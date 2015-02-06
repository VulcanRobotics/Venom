package org.usfirst.frc.team1218.subsystem.elevator;

import edu.wpi.first.wpilibj.command.Command;
import src.org.usfirst.frc.team1218.robot.Robot;
import src.org.usfirst.frc.team1218.elevator.Elevator;

/**
 *
 */
public class C_ZeroElevator extends Command {

    public C_ZeroElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.configureElevatorMotorControllers();
    	Robot.elevator.configureElevatorMotorControllersForSpeedControl();
    	Robot.elevator.setElevatorSpeed(Robot.elevator.ZERO_SPEED);
    	setTimeout(7);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.elevator.liftMaster.getFaultRevLim()) {
    		//if bottom limit switch is hit
    		Robot.elevator.setElevatorSpeed(0.0);
    		Robot.elevator.liftMaster.setPosition(0);
    		return true;
    	}
    	if(isTimedOut()) {
    		System.out.println("Error: elevator has not hit limit after running for 7 seconds");
    		Robot.elevator.setElevatorSpeed(0.0);
    		return true;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
