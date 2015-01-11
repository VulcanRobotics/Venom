package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.Angle;
import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveModule extends Object{
	
	private final int moduleNumber; //Used to retrieve module specific offsets and modifiers

	private boolean moduleInverted = false; //Used to prevent robot from seeking more than 90 degrees to an angle by inverting both direction and power of module
	
	//Angle Controller
	private final Talon angleMotor;
	private final AngleEncoder angleEncoder;
	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	private static final double ANGLE_MOTOR_MAXIMUM_POWER = 0.85; //Max angleController motor power.
	
	//Drive Motor
	private final Talon driveMotor;
	private static final double WHEEL_POWER_SCALE = 0.5;
	
	private static final double ANGLE_UPDATE_MINIMUM_WHEEL_POWER = 0.1; //Module angle will not be updated by setAngle if new power is < this.

	//Module Specific Configuration
	private static final boolean[] MODULE_REVERSED = {false, false, true, true};
	private static final double[] MODULE_ANGLE_OFFSET = {40.0, -36.0, -22.0, 85.0};//Zero Code
		
	//Zeroing Sensor
	private final DigitalInput zeroSensor;
	private static final double RESET_TURN_POWER = 0.25;
	private boolean isZeroing = false;
	
	public SwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveMotor = new Talon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.angleMotor = new Talon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleEncoder = new AngleEncoder(RobotMap.SM_ENCODER_A[moduleNumber], RobotMap.SM_ENCODER_B[moduleNumber], MODULE_REVERSED[moduleNumber]);
		this.zeroSensor = new DigitalInput(RobotMap.SM_ZERO[moduleNumber]);//ZERO CODE
		this.angleController = new PIDController(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D, angleEncoder, angleMotor);
		this.angleController.setInputRange(0.0, 360.0);
		this.angleController.setOutputRange(-ANGLE_MOTOR_MAXIMUM_POWER, ANGLE_MOTOR_MAXIMUM_POWER);
		this.angleController.setContinuous();
		this.angleEncoder.reset();
		this.angleController.enable();
	}
	
	/**
	 * Intelligently sets module to prevent travel farther than 90 degrees for any position.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setValues(double angle, double power) {//TODO Test this method on robot
		double angleChange = Angle.angleDifference(angleController.getSetpoint(), angle); //Calculate angle difference
		if (Math.abs(power) >= ANGLE_UPDATE_MINIMUM_WHEEL_POWER) {
			if (angleChange > 90.0) moduleInverted = !moduleInverted; //fakes inversion to jump orientation 180 degrees
			setAngle(angle);
		}
		setPower(power);
	}
	
	/**
	 * Helper class that allows vectors to be written to a module.
	 * @param vector Vector with a magnitude of 1.0 or less.
	 */
	public void setVector(Vector vector) {
			setValues(vector.getAngle(), vector.getMagnitude());
	}
	
	/**
	 * angleController setpoint should always be set through this method because writing directly to angleController will not apply offsets
	 * @param angle Desired wheel angle. Can be any value
	 */
	public void setAngle(double angle) {
		angle = Angle.get360Angle(angle + MODULE_ANGLE_OFFSET[moduleNumber] + ((moduleInverted) ? 180 : 0)); //Applies angle offset and inversion settings then coerces value to 0 - 360
		this.angleController.setSetpoint(((MODULE_REVERSED[moduleNumber]) ? 360.0 - angle : angle)); //applies module specific direction preferences
	}
	
	/**
	 * Sets motor power safely and applies module offsets.
	 * @param power desired motor magnitude and direction
	 */
	public void setPower(double power) {
		if(Math.abs(power) > 1) {
			System.out.println("Invalid power written to SM_" + moduleNumber + ". Power " + power + " is out of range");
			this.driveMotor.set(0.0);
		} else {
			this.driveMotor.set(power * WHEEL_POWER_SCALE * ((MODULE_REVERSED[moduleNumber]) ? 1.0 : -1.0) * ((moduleInverted) ? -1.0 : 1.0));
		}
	}
	
	//*****************
	//**  ZERO CODE  **
	//*****************
	
	public void setZeroing() {
		this.isZeroing = true;
	}
	
	public boolean getZeroing() {
		return this.isZeroing;
	}
	
	/**
	 * State Machine that drives C_ResetModules
	 * ZERO CODE
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
	///////////////////
	///////////////////
	///////////////////
	
	public class AngleEncoder extends Encoder {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500.0;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		private static final double ENCODER_DEGREE_SCALE = (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO;
		
		public AngleEncoder(int aChannel, int bChannel, boolean reverseDirection) {
			super(aChannel, bChannel, reverseDirection);
		}
		
		@Override
		public double pidGet() {
			return Angle.get360Angle(get() * ENCODER_DEGREE_SCALE);
		}
	}
	
	public void publishValues() {
		SmartDashboard.putNumber("SM" + moduleNumber + " Angle", angleEncoder.pidGet());
		SmartDashboard.putNumber("SM_" + moduleNumber + " WheelPower", driveMotor.get());
		SmartDashboard.putBoolean("SM_" + moduleNumber + " isZeroing", isZeroing);
		SmartDashboard.putBoolean("SM_" + moduleNumber + " AngleControllerEnabled", angleController.isEnable());
		SmartDashboard.putBoolean("SM_" + moduleNumber + " ZeroSensor", zeroSensor.get());
	}
}
