package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.command.Command;

/**
 * While engaged only forward and backward movement is possible. Goal is to make backing off of stacks easy.
 *@author afiol-mahon
 */
public class C_LockDrive extends Command {
	
	public C_LockDrive() {
		requires(Robot.swerveDrive);
		Robot.swerveDrive.setFieldCentricDriveMode(false);
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		Robot.swerveDrive.powerDrive(
				new Vector(0, OI.getDriverLeftJoystickVector().getY()),
				Math.pow(OI.getDriverRightX(), 3));
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.swerveDrive.setFieldCentricDriveMode(true);
	}

	@Override
	protected void interrupted() {
		this.end();
	}
}
