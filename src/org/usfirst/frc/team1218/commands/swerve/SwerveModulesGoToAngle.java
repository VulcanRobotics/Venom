package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwerveModulesGoToAngle extends Command{
	
	private double angle;
	
	public SwerveModulesGoToAngle(double angle) {
		requires(Robot.swerveDrive);
		this.angle = angle;
	}
	
	@Override
	protected void initialize() {
		Robot.swerveDrive.setModuleAngles(angle);
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.swerveDrive.isAnglePIDOnTarget() || this.isTimedOut();
	}
	
	@Override
	protected void end() {
		System.out.println("SwerveDrive on angle: " + angle);
	}
	
	@Override
	protected void interrupted() {
		System.out.println("Module Go To Angle Interrupted...");
	}
	
}