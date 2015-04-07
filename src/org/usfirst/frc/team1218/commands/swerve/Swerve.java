package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author afiol-mahon
 *
 */
public class Swerve extends Command {
	public Swerve() {
		requires(Robot.swerveDrive);
	}
	
	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		Robot.swerveDrive.powerDrive(
				OI.getDriverLeftJoystickVector(),
				OI.getSwerveRotationAxis());
	}
	
	@Override
	protected boolean isFinished() {return false;}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}

}
