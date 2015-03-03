package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WaitForTote extends Command {

	double timeout = 0;
	
    public C_WaitForTote(double timeout) {
    	requires(Robot.elevator);
    	this.timeout = timeout;
    }

    protected void initialize() {
    	setTimeout(timeout);
    	
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.elevator.getHasTote() || isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
