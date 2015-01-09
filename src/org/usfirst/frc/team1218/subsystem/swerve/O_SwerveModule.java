package org.usfirst.frc.team1218.subsystem.swerve;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;

public class O_SwerveModule {
	
	private int SMNumber;
	
	private final Talon driveMotor;
	private final Talon turnMotor;
	
	private final SMEncoder turnEncoder;
	
	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	private static final int[] MODULE_WHEELDIR = {-1, -1, 1, 1};
	
	public O_SwerveModule(int SMNumber, int drivePort, int turnPort, int encASource, int encBSource) {
		this.SMNumber = SMNumber;
		this.driveMotor = new Talon(drivePort);
		this.turnMotor = new Talon(turnPort);
		this.turnEncoder = new SMEncoder(encASource, encBSource);
		this.angleController = new PIDController(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D, turnEncoder, turnMotor);
		this.angleController.setInputRange(0.0, 360.0);
		this.angleController.setOutputRange(-0.85, 0.85);
		this.angleController.setContinuous();
		this.turnEncoder.encoder.reset();
		this.angleController.enable();
	}
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void updateSM(double angle, double power) {
		this.angleController.setSetpoint(angle);
		this.driveMotor.set(power * MODULE_WHEELDIR[SMNumber]);
	}
	
	public void resetModule() {
		//TODO
	}
	
	
	public class SMEncoder implements PIDSource {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		private final Encoder encoder;
		
		public SMEncoder(int aSource, int bSource) {
			this.encoder = new Encoder(aSource, bSource);
		}
		
		@Override
		public double pidGet() {
			double encoderOutput = (encoder.get() * (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO) % 360.0;
			if (encoderOutput < 0) {
				encoderOutput = 360.0 - Math.abs(encoderOutput); //Converts encoderOutput from -180 to 180 degrees to 0 to 360 scale.
			}
			return encoderOutput;
		}
	}
}
