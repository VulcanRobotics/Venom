package org.usfirst.frc.team1218.subsystem.swerve.command;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Swerve extends Command {

	public Swerve() {
		requires(Robot.swerveSystem);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.swerveSystem.swerveDrive();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
