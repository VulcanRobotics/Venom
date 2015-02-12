package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ManualControl extends Command {
	
	private double power;
	
    public C_ManualControl(double power) {
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
