package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_TogglePower extends Command {

    public C_TogglePower() {
    	requires(Robot.swerveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveSystem.toggleModulePower();
    	System.out.println("Max Power: " + Robot.swerveSystem.Module_Power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
