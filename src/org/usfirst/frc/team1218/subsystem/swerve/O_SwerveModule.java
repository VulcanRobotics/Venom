package org.usfirst.frc.team1218.subsystem.swerve;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class O_SwerveModule extends Object{
	
	private final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	private final Talon driveMotor;
	private final Talon angleMotor;
	
	private final DigitalInput zeroSensor;
	private final AngleEncoder angleEncoder;
	
	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	private boolean isZeroing = false;
	
	private static final boolean[] MODULE_REVERSED = {false, false, true, true};
	private static final double[] MODULE_ANGLE_OFFSET = {40.0, -36.0, -22.0, 85.0};
	
	private static final double RESET_TURN_POWER = 0.25;

	
	public O_SwerveModule(int moduleNumber, int driveMotorChannel, int angleMotorChannel, int encoderAChannel, int encoderBChannel, int zeroSensorChannel) {
		this.moduleNumber = moduleNumber;
		this.driveMotor = new Talon(driveMotorChannel);
		this.angleMotor = new Talon(angleMotorChannel);
		this.angleEncoder = new AngleEncoder(encoderAChannel, encoderBChannel, MODULE_REVERSED[moduleNumber]);
		this.zeroSensor = new DigitalInput(zeroSensorChannel);
		this.angleController = new PIDController(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D, angleEncoder, angleMotor);
		this.angleController.setInputRange(0.0, 360.0);
		this.angleController.setOutputRange(-0.85, 0.85);
		this.angleController.setContinuous();
		this.angleEncoder.reset();
		this.angleController.enable();
	}
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */

	public void setZeroing() {
		this.isZeroing = true;
	}
	
	public boolean getZeroing() {
		return this.isZeroing;
	}
	
	public void setValues(double angle, double power) {
		if (Math.abs(power) > 0.1) setAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		this.driveMotor.set(0.4 * power * ((MODULE_REVERSED[moduleNumber]) ? 1.0 : -1.0)); //Applies module specific motor preferences
		//XXX added 0.75 motor scale
	}
	
	/**
	 * angleController setpoint should always be set through this method in order to apply zeroing offsets
	 * @param angle Desired wheel angle. Can be any value
	 */
	public void setAngle(double angle) {
		angle += MODULE_ANGLE_OFFSET[moduleNumber]; //adds angle zeroing point offset to the modules written angle.
		if (angle < 0) angle = 360.0 - Math.abs(angle); //forces angle to be a positive value.
		angle = angle % 360; //forces angle to be less than 360
		this.angleController.setSetpoint(((MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle)); //applies module specific direction preferences
	}
	
	/**
	 * State Machine that drives C_ResetModules
	 */
	public void zeroModule() { //TODO Consolidate zero code
		this.driveMotor.set(0.0);
		if (zeroSensor.get()) {
			//At Zero
			this.angleEncoder.reset();
			this.angleMotor.set(0.0);
			this.setAngle(0);
			this.angleController.enable();
			this.isZeroing = false;
		}else {
			//Finding Zero
			this.angleController.disable();
			this.angleMotor.set(RESET_TURN_POWER);
			this.setZeroing();
		}
	}
	
	public class AngleEncoder extends Encoder {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		
		public AngleEncoder(int aChannel, int bChannel, boolean reverseDirection) {
			super(aChannel, bChannel, reverseDirection);
		}
		
		@Override
		public double pidGet() {
			double encoderAngle = (get() * (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO) % 360.0;
			if (encoderAngle < 0) {
				encoderAngle = 360.0 - Math.abs(encoderAngle); //Converts encoderOutput from -180 to 180 degrees to 0 to 360 scale.
			}
			return (encoderAngle);
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
