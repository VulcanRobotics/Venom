package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

/**
 * @author afiol-mahon
 */
public class SwerveModule_DIO extends SwerveModule {
	
	public AngleEncoder angleEncoder;
	private final PIDController anglePIDController;
	
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	public SwerveModule_DIO(int moduleNumber) {
		super(moduleNumber);
		this.angleEncoder = new AngleEncoder(moduleNumber);
		//Initialize PID
		this.anglePIDController = new PIDController(
				ANGLE_CONTROLLER_P,
				ANGLE_CONTROLLER_I,
				ANGLE_CONTROLLER_D,
				angleEncoder,
				angleController);
		this.anglePIDController.setInputRange(0.0, 360.0);
		this.anglePIDController.setOutputRange(-MAX_ANGLE_CONTROLLER_POWER, MAX_ANGLE_CONTROLLER_POWER);
		this.anglePIDController.setContinuous();
		//Begin Module
		this.angleEncoder.reset();
		this.anglePIDController.enable();
	}
	
	@Override
	public double getEncoderAngle() {
		return angleEncoder.pidGet();
	}
	
	@Override
	public int getEncoderIndexCount() {
		return angleEncoder.getFPGAIndex();
	}
	
	@Override
	public void setPIDAngle(double angle) {
		this.anglePIDController.setSetpoint(angle); //applies module specific direction preferences
	}
	
	public class AngleEncoder extends Encoder {
		
		public AngleEncoder(int moduleNumber) {
			super(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber],
					RobotMap.SM_ANGLE_ENCODER_B[moduleNumber],
					RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]
					);
			this.setDistancePerPulse(ENCODER_CLICK_TO_DEGREE);
		}
		
		@Override
		public double pidGet() {
			double angle = get();
			angle += MODULE_ANGLE_OFFSET[moduleNumber];
			angle = Angle.get360Angle(angle);
			return angle;
		}
	}
}