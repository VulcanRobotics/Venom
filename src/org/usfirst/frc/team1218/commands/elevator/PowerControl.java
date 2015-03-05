package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class PowerControl extends Command {
	
	private double power;
	
    public PowerControl(double power) {
        requires(Robot.elevator);
        this.power = power;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.elevator.setPower(power);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevator.setPower(0.0);
    }

    protected void interrupted() {
    	end();
    }
}
