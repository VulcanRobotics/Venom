package org.usfirst.frc.team1218.subsystem.swerve;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;

public class O_SwerveModule {
	private Talon driveMotor;
	private Talon turnMotor;
	public SMEncoder turnEncoder;
	public PIDController angleController;
	private static double ANGLE_CONTROLLER_P = -0.01;
	private static double ANGLE_CONTROLLER_I = 0.0;
	private static double ANGLE_CONTROLLER_D = 0.0;

	
	public O_SwerveModule(int drivePort, int turnPort, int encASource, int encBSource) {
		driveMotor = new Talon(drivePort);
		turnMotor = new Talon(turnPort);
		turnEncoder = new SMEncoder(encASource, encBSource);
		angleController = new PIDController(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D, turnEncoder, turnMotor);
		angleController.setInputRange(0.0, 360.0);
		angleController.setOutputRange(-0.85, 0.85);
		angleController.setContinuous();
		turnEncoder.encoder.reset();
		angleController.enable();
	}
	
	public void updateSM(double angle, double power) {
		angleController.setSetpoint(angle);
		driveMotor.set(power);
	}
	
	public class SMEncoder implements PIDSource {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		
		private Encoder encoder;
		public SMEncoder(int aSource, int bSource) {
			encoder = new Encoder(aSource, bSource);
		}
		@Override
		public double pidGet() {
			double encoderOutput = (encoder.get() * (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO) % 360.0;
			if (encoderOutput < 0) {
				encoderOutput = 360.0 - Math.abs(encoderOutput);
			}
			return encoderOutput;
		}

	}
}
