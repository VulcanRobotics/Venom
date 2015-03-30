package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class RightDartManualControl extends Command {

	private double power;
	
    public RightDartManualControl(double power) {
    	requires(Robot.fourBar);
    	this.power = power;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.fourBar.rightDartManual(power);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.fourBar.rightDartManual(0);
    }

    protected void interrupted() {
    	end();
    }
}
