package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author afiol-mahon
 *
 */
public class C_MaintainHeading extends Command {
	
	
	public C_MaintainHeading() {
		requires(Robot.swerveDrive);
	}
	
	@Override
	protected void initialize() {
		Robot.swerveDrive.enableHeadingController(Robot.swerveDrive.getHeading());
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.swerveDrive.disableHeadingController();
	}

	@Override
	protected void interrupted() {
		this.end();
	}
}