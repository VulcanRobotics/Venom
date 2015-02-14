package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Swerve Class that handles all module logic except for the angle writing itself
 * @author afiol-mahon
 */
public class SwerveModule {
	
	protected final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	protected double robotCentricSetpointAngle = 0; //Angle relative to front of robot
	
	//Module Runtime Settings
	private boolean invertModule = false;
	private boolean stableMode = false;
	
	protected final CANTalon driveWheelController;
	//Drive Wheel Controller Constants
	private static final double DRIVE_WHEEL_RADIUS = 1.5; //inches
	private static final double DRIVE_WHEEL_CIRCUMFERENCE = (2 * Math.PI * DRIVE_WHEEL_RADIUS); //inches
	private static final double DRIVE_WHEEL_FEET_PER_ROTATION = 12 / DRIVE_WHEEL_CIRCUMFERENCE;//feet
	private static final double DRIVE_ENCODER_TO_WHEEL_ROTATION_RATIO = 6 / 5; //Gearing Ratio
	private static final double DRIVE_ENCODER_CLICKS = 500; //Clicks in 1 encoder rotation
	private static final double DRIVE_ENCODER_CLICKS_PER_REV = DRIVE_ENCODER_CLICKS * DRIVE_ENCODER_TO_WHEEL_ROTATION_RATIO; //Encoder Clicks Per 1 Wheel Revolution
	private static final double DRIVE_WHEEL_FEET_TO_CLICK_RATIO = DRIVE_WHEEL_FEET_PER_ROTATION * DRIVE_ENCODER_CLICKS_PER_REV;//converts Feet/Second to encoder clicks
	//PID
	private static final double DRIVE_WHEEL_VELOCITY_P = 0.1;
	private static final double DRIVE_WHEEL_VELOCITY_I = 0.001;
	private static final double DRIVE_WHEEL_VELOCITY_D = 0.0;
	//Limits
	private static final double MAX_VELOCITY = 15; //feet per second
	private static final double DRIVE_POWER_SCALE = 0.5;
	
	protected AngleEncoder angleEncoder;
	protected final PIDController anglePIDController;
	
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	protected final CANTalon angleController;
	//Angle Controller Constants
	protected static final double MAX_ANGLE_CONTROLLER_POWER = 0.7;
	
	protected static final double[] ALPHA_MODULE_ANGLE_OFFSET = {6.0, 161.0, -66.5, 128.0};
	protected static final double[] BETA_MODULE_ANGLE_OFFSET = {-2.0, 133.0, -15.0, -145.0};
	protected double[] moduleAngleOffset = ALPHA_MODULE_ANGLE_OFFSET;
	
	protected static final double ENCODER_CLICKS_PER_REVOLUTION = 500.0;
	protected static final double ENCODER_CLICK_TO_DEGREE = 360.0 / ENCODER_CLICKS_PER_REVOLUTION; //Degrees over Number of Clicks
	protected static final double ENCODER_DEGREE_TO_CLICK = ENCODER_CLICKS_PER_REVOLUTION / 360.0;
	
	private static final double STABLE_MODE_RAMP_RATE = 0.0;
	private static final double NORMAL_MODE_RAMP_RATE = 0.0;
	
	public SwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		if (Preferences.getInstance().getBoolean("isBeta", false)) {
			this.moduleAngleOffset = BETA_MODULE_ANGLE_OFFSET;
			System.out.println("Beta Swerve Profile Detected");
		} else {
			this.moduleAngleOffset = ALPHA_MODULE_ANGLE_OFFSET;
			System.out.println("Alpha Swerve Profile Detected");
		}
		this.driveWheelController = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.driveWheelController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.driveWheelController.enableBrakeMode(false);
		this.driveWheelController.enableLimitSwitch(false, false);
		this.driveWheelController.enableForwardSoftLimit(false);
		this.driveWheelController.enableReverseSoftLimit(false);
		this.driveWheelController.setPID(DRIVE_WHEEL_VELOCITY_P, DRIVE_WHEEL_VELOCITY_I, DRIVE_WHEEL_VELOCITY_D);
		this.angleController = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleController.enableLimitSwitch(false, false);
		this.angleEncoder = new AngleEncoder(moduleNumber, moduleAngleOffset[moduleNumber]);
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

	/**
	 * Get the value of the encoder currently used on the angle
	 * @returns degree position of encoder
	 */
	public double getEncoderAngle() {
		return angleEncoder.pidGet();
	}

	public int getEncoderIndexCount() {
		return angleEncoder.getIndexCount();
	}

	/**
	 * @return true if the module is currently operating in stable mode.
	 */
	public boolean isStableMode() {
		return stableMode;
	}
	
	/**
	 * Puts robot into a less agile and more precise control mode that is intended for safely controlling large stacks
	 * without fear of tipping them.
	 * @param enabled
	 */
	protected void setStableMode(boolean enabled) {
		if (enabled) {
			driveWheelController.setVoltageRampRate(STABLE_MODE_RAMP_RATE);
			stableMode = true;
		} else {
			driveWheelController.setVoltageRampRate(NORMAL_MODE_RAMP_RATE);
		}
		driveWheelController.enableBrakeMode(enabled); 
		stableMode = enabled;
		SmartDashboard.putBoolean("SM_" + moduleNumber + "_isStableMode", stableMode);
	}
	
	/**
	 * Method that controls final stage of setting a properly offset angle
	 * to the swerve drive.
	 * The purpose of this is to allow the swerveModule to handle as much of
	 * the uniform calculations that happen in both modules as possible
	 * so that both SwerveModule Classes are as small as possible
	 * so they just have to define the last part of the angle write
	 * @param angle Desired wheel angle. Can be any value
	 */
	public void setPIDAngle(double angle) {
		this.anglePIDController.setSetpoint(angle); //applies module specific direction preferences
	}
	
	/**
	 * Writes a power to the wheel that corresponds with module settings.
	 * @param power
	 */
	public void setWheelPower(double power) {
		if (Math.abs(power) > 1) {
			System.out.println("Illegal power " + power + " written to module: " + moduleNumber);
		} else {
			power *= (invertModule) ? -1.0 : 1.0;
			this.driveWheelController.changeControlMode(ControlMode.PercentVbus);
			this.driveWheelController.set(DRIVE_POWER_SCALE * power); //Applies module specific motor preferences
		}
	}
	
	/**
	 * Set the wheels to any angle that is relative to the robot's front.
	 * @param angle
	 */
	public void setRobotAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.robotCentricSetpointAngle) > 90) invertModule = !invertModule;
		this.robotCentricSetpointAngle = angle;
		angle += (invertModule) ? 180 : 0;
		angle = 360 - angle;
		angle = Angle.get360Angle(angle);
		setPIDAngle(angle);
	}
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setAngleAndPower(double angle, double power) {
		if (Math.abs(power) > 0.1) setRobotAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		setWheelPower(power);
	}
	
	public void setAngleAndSpeed(double angle, double percentSpeed) {
		if (Math.abs(percentSpeed) > 0.1) {
			setRobotAngle(angle);
			setWheelSpeed(percentSpeed * MAX_VELOCITY);
		} else {
			setWheelPower(0.0);
		}
	}
	
	/**
	 * Directly Write the magnitude and power of a given angle to the module.
	 * Makes it very straightforward to write any calculated vector to the module.
	 * @param vector
	 */
	public void setDriveVector(Vector vector) {
		setAngleAndPower(vector.getAngle(), vector.getMagnitude());
	}
	
	/**
	 * write a speed that should be maintained to the controller
	 * @param speed ft/s
	 */
	public void setWheelSpeed(double speed) {
		speed *= DRIVE_WHEEL_FEET_TO_CLICK_RATIO;
		if (Math.abs(speed) > MAX_VELOCITY) {
			System.out.println("Illegal speed " + speed + "(ft/s) written to module: " + moduleNumber);
		} else {
			speed *= (invertModule) ? -1.0 : 1.0;
			this.driveWheelController.changeControlMode(ControlMode.Speed);
			this.driveWheelController.set(speed * 10); //Multiply by 10 because PID controller takes Units per decisecond
		}
	}
	
	/**
	 * Update the dashboard with current module information.
	 */
	public void syncDashboard() {
		String prefix = "SwerveModule [" + moduleNumber + "]: ";
		SmartDashboard.putNumber(prefix + "WheelPower", driveWheelController.get());
		SmartDashboard.putNumber(prefix + "EncoderAngle", getEncoderAngle());
		SmartDashboard.putNumber(prefix + "RobotCentricAngle", robotCentricSetpointAngle);
		SmartDashboard.putNumber(prefix + "IndexCount", getEncoderIndexCount());
	}
}
