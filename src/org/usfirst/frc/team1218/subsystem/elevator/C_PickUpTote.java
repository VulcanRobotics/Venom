package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_PickUpTote extends Command {

	boolean hasToteInBrushes;
	
    public C_PickUpTote() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	requires(Robot.totePickup);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(5);
    	hasToteInBrushes = false;
    	Robot.totePickup.setIntakePower(Robot.totePickup.TOTE_INTAKE_POWER);
    	Robot.elevator.configureElevatorMotorControllersForPID();
    	Robot.elevator.setElevatorSetpoint(Robot.elevator.ELEVATOR_RAISE_POSITION);
    }

    // Called repeatedly when this Command is scheduled to run
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
    		//if the elevator jsa grabbed tote with brushes, it should lift it to max height
    		Robot.elevator.setElevatorSetpoint(Robot.elevator.ELEVATOR_RAISE_POSITION);
    	}
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//ends when has a tote and that tote clears other totes
        return isTimedOut() | (hasToteInBrushes & Robot.elevator.getPosition() > Robot.elevator.ELEVATOR_CLEARNCE_POSITION);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
