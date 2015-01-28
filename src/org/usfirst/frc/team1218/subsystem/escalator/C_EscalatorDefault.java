package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_EscalatorDefault extends Command {

    public C_EscalatorDefault() {
        requires(Robot.escalator);
    }

    protected void initialize() {
    	//TODO disable pid control
    }

    protected void execute() {
    	//TODO follow operator joysticks
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	//TODO reenable pid control
    }

    protected void interrupted() {
    	end();
    }
}
