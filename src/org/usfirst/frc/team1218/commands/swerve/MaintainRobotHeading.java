package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class MaintainRobotHeading extends Command {
	
	boolean canFinish = false;
	double heading = 0;
	public MaintainRobotHeading() {
		requires(Robot.swerveDrive);
	}
	
	public MaintainRobotHeading(double heading){
		this.heading = heading;
		canFinish = true;
	}
	
	@Override
	protected void initialize() {
		Robot.swerveDrive.enableHeadingController(canFinish ? heading : Robot.swerveDrive.getHeading());
	}

	@Override
	protected void execute() {
		Robot.swerveDrive.powerDrive(
				OI.getDriverLeftJoystickVector(),
				OI.getSwerveRotationAxis());
	}

	@Override
	protected boolean isFinished() {
		return canFinish && Robot.swerveDrive.isHeadingOnTarget();
	}

	@Override
	protected void end() {
		Robot.swerveDrive.disableHeadingController();
		Robot.swerveDrive.setRawWheelPower(0);
		
	}

	@Override
	protected void interrupted() {
		this.end();
	}
}