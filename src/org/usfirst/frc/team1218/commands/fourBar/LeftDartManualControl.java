package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class LeftDartManualControl extends Command {

	private double power;
	
    public LeftDartManualControl(double power) {
    	requires(Robot.fourBar);
    	this.power = power;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.fourBar.leftDartManual(power);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.fourBar.leftDartManual(0);
    }

    protected void interrupted() {
    	end();
    }
}