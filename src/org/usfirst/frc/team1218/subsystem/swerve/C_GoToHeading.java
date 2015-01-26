package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

public class C_GoToHeading extends Command implements PIDOutput, PIDSource{

	PIDController headingController;
	
	public C_GoToHeading() {
		requires(Robot.swerveSystem);
	}
	
	private static final double HEADING_CONTROLLER_P = 0.03;
	private static final double HEADING_CONTROLLER_I = 0.0;
	private static final double HEADING_CONTROLLER_D = 0.0;
	
	@Override
	protected void initialize() {
		headingController = new PIDController(
				HEADING_CONTROLLER_P,
				HEADING_CONTROLLER_I,
				HEADING_CONTROLLER_D,
				this,
				this);
		headingController.setOutputRange(-1.0, 1.0);
		headingController.setInputRange(0, 360);
		headingController.enable();
	}

	@Override
	protected void execute() {
		headingController.setPID(0.001, 0.001, 0.00);
		if (OI.getRightJoystickVector().getMagnitude() > 0.2) {
			headingController.setSetpoint(0);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		headingController.disable();
	}

	@Override
	protected void interrupted() {
		this.end();
	}
	
	@Override
	public void pidWrite(double output) {
		Robot.swerveSystem.swerveDrive(
				OI.getLeftJoystickVector(),
				output,
				Robot.swerveSystem.navModule.getYaw()
				);
	}

	@Override
	public double pidGet() {
		return Robot.swerveSystem.navModule.getYaw();
	}
}