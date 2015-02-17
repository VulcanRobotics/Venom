package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_GoToBottom extends Command {

    public C_GoToBottom() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("going to bottom");
    	Robot.elevator.setPower(-Robot.elevator.ELEVATOR_MANUAL_POSITIONING_POWER);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevator.getBottomLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("going to bottom finished");
    	Robot.elevator.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
