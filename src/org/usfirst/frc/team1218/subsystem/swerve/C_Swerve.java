package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class C_Swerve extends Command {

	public C_Swerve() {
		requires(Robot.swerveSystem);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.swerveSystem.swerveDrive(
				OI.getDriverLeftJoystickVector(),
				Math.pow(OI.getDriverRightX(), 3),
				Robot.swerveSystem.navModule.getYaw()
				);
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
