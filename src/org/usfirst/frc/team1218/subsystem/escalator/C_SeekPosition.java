package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_SeekPosition extends Command {

	private double setpoint;
	
    public C_SeekPosition(double setpoint) {
        requires(Robot.escalator);
        this.setpoint = setpoint;
    }

    protected void initialize() {
    	Robot.escalator.enableDarts();
    	Robot.escalator.dartPositionMode();
    	Robot.escalator.setDarts(setpoint);
    	System.out.println("[Escalator]: Seeking Position...");
    }

    protected void execute() {
    	Robot.escalator.dartL.setPID(10.0, 0.0, 0.0);
    	System.out.println(Robot.escalator.dartL.getClosedLoopError());
    }

    protected boolean isFinished() {
        //Relies on OI Button to end command when released
    	return false;
    }

    protected void end() {
    	Robot.escalator.disableDarts();
    }

    protected void interrupted() {
    	end();
    }
}