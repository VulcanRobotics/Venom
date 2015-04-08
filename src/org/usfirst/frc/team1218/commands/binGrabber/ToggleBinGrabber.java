package org.usfirst.frc.team1218.commands.binGrabber;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleBinGrabber extends Command {

    public ToggleBinGrabber() {
    	requires(Robot.binGrabber);
    }

    protected void initialize() {
    	Robot.binGrabber.set(!Robot.binGrabber.get());
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
