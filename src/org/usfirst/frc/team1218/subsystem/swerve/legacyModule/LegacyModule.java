package org.usfirst.frc.team1218.subsystem.swerve.legacyModule;

import org.usfirst.frc.team1218.math.Angle;
import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.VulcanSwerveModule;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LegacyModule extends VulcanSwerveModule {
	
	private boolean isZeroing = false;

	private final CANTalon angleMotor;
	private final DigitalInput zeroSensor;
	private final AngleEncoder angleEncoder;
	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	private static final double[] MODULE_ANGLE_OFFSET = {40.0, -36.0, -22.0, 85.0};
	private double angle = 0; //Current module angle
	
	private static final boolean[] MODULE_REVERSED = {false, false, true, true};
	
	private boolean invertModule = false;
	
	private static final double RESET_TURN_POWER = 0.25;
	private static final double ANGLE_MOTOR_OUTPUT_RANGE = 1.0;
	
	public LegacyModule(int moduleNumber) {
		super(moduleNumber);
		this.angleMotor = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleEncoder = new AngleEncoder(RobotMap.SM_ENCODER_A[moduleNumber], RobotMap.SM_ENCODER_B[moduleNumber], MODULE_REVERSED[moduleNumber]);
		this.zeroSensor = new DigitalInput(RobotMap.SM_ZERO[moduleNumber]);
		this.angleController = new PIDController(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D, angleEncoder, angleMotor);
		this.angleController.setInputRange(0.0, 360.0);
		this.angleController.setOutputRange(-ANGLE_MOTOR_OUTPUT_RANGE, ANGLE_MOTOR_OUTPUT_RANGE);
		this.angleController.setContinuous();
		this.angleEncoder.reset();
		this.angleController.enable();
	}
	
	@Override
	public void setAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.angle) > 90) {
			invertModule = !invertModule;
		}
		this.angle = angle;
		angle += (invertModule) ? 180 : 0;
		angle += MODULE_ANGLE_OFFSET[moduleNumber]; //adds angle zeroing point offset to the modules written angle.
		angle = (MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle;
		angle = Angle.get360Angle(angle);
		displayAngle = angle;
		this.angleController.setSetpoint(angle); //applies module specific direction preferences
	}
	
	public void setZeroing() {
		this.isZeroing = true;
	}
	
	@Override
	public boolean getZeroing() {
		return this.isZeroing;
	}
	
	/**
	 * State Machine that drives C_ResetModules
	 */
	public void zeroModule() {
		this.driveMotor.set(0.0);
		if (zeroSensor.get()) {
			//At Zero
			this.angleEncoder.reset();
			this.angleMotor.set(0.0);
			this.setAngle(0.0);
			this.angleController.enable();
			this.isZeroing = false;
		}else {
			//Finding Zero
			this.angleController.disable();
			this.angleMotor.set(RESET_TURN_POWER);
			this.setZeroing();
		}
	}
	
	public void publishValues() {
		super.publishValues();
		SmartDashboard.putBoolean("SM" + moduleNumber + "_isZeroing", isZeroing);
		SmartDashboard.putBoolean("SM" + moduleNumber + "_ZeroSensor", zeroSensor.get());
	}
	
	public class AngleEncoder extends Encoder {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		private static final double ENCODER_CLICK_DEGREE_RATIO = (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO;
		
		public AngleEncoder(int aChannel, int bChannel, boolean reverseDirection) {
			super(aChannel, bChannel, reverseDirection);
		}
		
		@Override
		public double pidGet() {
			return Angle.get360Angle(get() * ENCODER_CLICK_DEGREE_RATIO);
		}
	}
}
