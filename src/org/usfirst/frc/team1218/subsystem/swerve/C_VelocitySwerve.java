package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author afiol-mahon
 *
 */
public class C_VelocitySwerve extends Command {
	
	private boolean robotCentric;
	private boolean lastButtonRobotCentricState = false;
	
	public C_VelocitySwerve() {
		requires(Robot.swerveDrive);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if(OI.robotCentricToggle.get() && !lastButtonRobotCentricState) {
			robotCentric = !robotCentric;
		}
		lastButtonRobotCentricState = OI.robotCentricToggle.get();
		
		Robot.swerveDrive.velocityDrive(
				OI.getDriverLeftJoystickVector(),
				Math.pow(OI.getDriverRightX(), 3),
				(!robotCentric) ? Robot.swerveDrive.getHeading() : 0
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
