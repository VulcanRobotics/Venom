package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_RunBinIntake extends Command {

    public C_RunBinIntake() {
        requires(Robot.escalator);
    }

    protected void initialize() {
    	Robot.escalator.setIntake(1.0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.escalator.setIntake(0.0);
    }

    protected void interrupted() {
    }
}
