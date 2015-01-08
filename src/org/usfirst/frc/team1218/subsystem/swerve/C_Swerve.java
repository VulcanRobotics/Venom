package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class C_Swerve extends Command {

	public C_Swerve() {
		requires(Robot.swerve);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
	   // Robot.swerve.swerveDrive();
		System.out.println("Swerve commented out");
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
