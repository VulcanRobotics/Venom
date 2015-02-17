package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class C_ModuleGoToAngle extends Command{
	
	private double angle;
	
	public C_ModuleGoToAngle(double angle) {
		requires(Robot.swerveDrive);
		this.angle = angle;
	}
	
	@Override
	protected void initialize() {
		Robot.swerveDrive.module.stream().forEach(m -> {
			m.setAngle(angle);
		});
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.swerveDrive.isAnglePIDOnTarget();
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