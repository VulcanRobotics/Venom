package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_OpenGrabber extends Command {

    public C_OpenGrabber() {
        requires(Robot.escalator);
    }

    protected void initialize() {
    	Robot.escalator.openGrabber(true);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.escalator.openGrabber(false);
    }

    protected void interrupted() {
    }
}
