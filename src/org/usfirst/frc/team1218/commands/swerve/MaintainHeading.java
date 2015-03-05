package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class MaintainHeading extends Command {
	
	
	public MaintainHeading() {
		requires(Robot.swerveDrive);
	}
	
	@Override
	protected void initialize() {
		Robot.swerveDrive.enableHeadingController(Robot.swerveDrive.getHeading());
	}

	@Override
	protected void execute() {
		Robot.swerveDrive.powerDrive(
				OI.getDriverLeftJoystickVector(),
				OI.getSwerveRotationAxis());
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