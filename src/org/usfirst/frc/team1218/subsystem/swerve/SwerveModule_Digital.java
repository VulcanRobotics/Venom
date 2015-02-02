package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author afiol-mahon
 */
public class SwerveModule_Digital extends SwerveModule {
	
	
	private final CANTalon angleMotor;
	public AngleEncoder angleEncoder;
	private static final double ENCODER_CLICK_DEGREE_RATIO = 360.0 / 500; //Degrees over Number of Clicks

	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = 0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	private static final double ANGLE_MOTOR_OUTPUT_RANGE = 1.0;
	
	public SwerveModule_Digital(int moduleNumber) {
		super(moduleNumber);
		this.angleMotor = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleEncoder = new AngleEncoder(moduleNumber);
		//Initialize PID
		this.angleController = new PIDController(
				ANGLE_CONTROLLER_P,
				ANGLE_CONTROLLER_I,
				ANGLE_CONTROLLER_D,
				angleEncoder,
				angleMotor);
		this.angleController.setInputRange(0.0, 360.0);
		this.angleController.setOutputRange(-ANGLE_MOTOR_OUTPUT_RANGE, ANGLE_MOTOR_OUTPUT_RANGE);
		this.angleController.setContinuous();
		//Begin Module
		this.angleEncoder.reset();
		this.angleController.enable();
	}
	
	public void setRealAngle(double angle) {
		this.angleController.setSetpoint(angle); //applies module specific direction preferences
	}
	
	@Override
	public void syncDashboard() {//TODO fix driver station value keys
		super.syncDashboard();
		SmartDashboard.putNumber("SM_" + moduleNumber + "_Encoder", angleEncoder.pidGet());
		SmartDashboard.putBoolean("SM_" + moduleNumber + "_IsAngleControllerEnabled", angleController.isEnable());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_AngleSetpoint", angleController.getSetpoint());
	}
	
	public class AngleEncoder extends Encoder {
		
		public AngleEncoder(int moduleNumber) {
			super(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber],
					RobotMap.SM_ANGLE_ENCODER_B[moduleNumber],
					RobotMap.SM_ANGLE_ENCODER_I[moduleNumber],
					MODULE_REVERSED[moduleNumber]
					);
			this.setDistancePerPulse(ENCODER_CLICK_DEGREE_RATIO);
		}
		
		@Override
		public double pidGet() {
			return Angle.get360Angle(get() * ENCODER_CLICK_DEGREE_RATIO);//XXX Redundant?
		}
	}
}