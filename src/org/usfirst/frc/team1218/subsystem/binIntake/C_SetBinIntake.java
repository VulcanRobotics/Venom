package org.usfirst.frc.team1218.subsystem.binIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_SetBinIntake extends Command {
	
	double power;
    
	public C_SetBinIntake(double power) {
    	requires(Robot.binIntake);
    	this.power = power;
    }

    protected void initialize() {
    	Robot.binIntake.setBinIntake(power);
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
