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
    	Robot.escalator.dartPositionMode();
    	Robot.escalator.enableDarts();
    	Robot.escalator.setDarts(setpoint);
    	System.out.println("[Escalator]: Seeking Position...");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.escalator.disableDarts();
    }

    protected void interrupted() {
    	end();
    }
}