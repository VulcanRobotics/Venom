package org.usfirst.frc.team1218.commands.binGrabber;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetGrabber extends Command {
	boolean state;
    public SetGrabber(boolean state) {
    	requires(Robot.binGrabber);
    	this.state = state;
    }

    protected void initialize() {
    	Robot.binGrabber.set(state);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
