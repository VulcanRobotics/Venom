package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Swerve Class that handles all module logic except for the angle writing itself
 * @author afiol-mahon
 */
public abstract class SwerveModule {
	
	protected final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	protected double robotCentricSetpointAngle = 0; //Angle relative to front of robot
	
	//Module Runtime Settings
	private boolean invertModule = false;
	private boolean stableMode = false;
	
	private final CANTalon driveWheelController;
	private static final double DRIVE_POWER_SCALE = 1.0;
	private static final double DRIVE_WHEEL_RADIUS = 1.5; //inches
	private static final double DRIVE_WHEEL_CIRCUMFERENCE = (Math.PI * Math.pow(DRIVE_WHEEL_RADIUS, 2)); //inches
	private static final double DRIVE_WHEEL_FEET_PER_ROTATION = 12 / DRIVE_WHEEL_CIRCUMFERENCE;//feet
	private static final double DRIVE_ENCODER_TO_WHEEL_ROTATION_RATIO = 6 / 5; //Gearing Ratio
	private static final double DRIVE_ENCODER_CLICKS = 500; //Clicks in 1 encoder rotation
	private static final double DRIVE_ENCODER_CLICKS_PER_REV = DRIVE_ENCODER_CLICKS * DRIVE_ENCODER_TO_WHEEL_ROTATION_RATIO; //Encoder Clicks Per 1 Wheel Revolution
	private static final double DRIVE_WHEEL_FEET_TO_CLICK_RATIO = DRIVE_WHEEL_FEET_PER_ROTATION * DRIVE_ENCODER_CLICKS_PER_REV;//converts Feet/Second to encoder clicks
	private static final double DRIVE_WHEEL_VELOCITY_P = 0.1;
	private static final double DRIVE_WHEEL_VELOCITY_I = 0.0;
	private static final double DRIVE_WHEEL_VELOCITY_D = 0.0;
	private static final double MAX_SPEED = 15; //feet per second

	
	protected final CANTalon angleController;
	protected static final double MAX_ANGLE_CONTROLLER_POWER = 0.7;
	protected static final double[] MODULE_ANGLE_OFFSET = {-2.0, 133.0, -15.0, -145.0};
	
	protected static final double ENCODER_CLICKS_PER_REVOLUTION = 500.0;
	protected static final double ENCODER_CLICK_TO_DEGREE = 360.0 / ENCODER_CLICKS_PER_REVOLUTION; //Degrees over Number of Clicks
	protected static final double ENCODER_DEGREE_TO_CLICK = ENCODER_CLICKS_PER_REVOLUTION / 360.0;
	protected static final double ENCODER_FEET_PER_CLICK = 0; //TODO Get Value
	
	
	private static final double NORMAL_MODE_RAMP_RATE = 0;
	private static final double STABLE_MODE_RAMP_RATE = 0;
	
	public SwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveWheelController = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.driveWheelController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.driveWheelController.enableBrakeMode(false);
		this.driveWheelController.enableLimitSwitch(false, false);
		this.driveWheelController.enableForwardSoftLimit(false);
		this.driveWheelController.enableReverseSoftLimit(false);
		this.driveWheelController.setPID(DRIVE_WHEEL_VELOCITY_P, DRIVE_WHEEL_VELOCITY_I, DRIVE_WHEEL_VELOCITY_D);
		this.angleController = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleController.enableLimitSwitch(false, false);
	}

	/**
	 * Get the value of the encoder currently used on the angle
	 * @returns degree position of encoder
	 */
	public abstract double getEncoderAngle();

	public abstract int getEncoderIndexCount();

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
    	OI.driver.setRumble(Joystick.RumbleType.kLeftRumble, (enabled) ? 0.15f : 0.0f);
    	OI.driver.setRumble(Joystick.RumbleType.kRightRumble, (enabled) ? 0.15f : 0.0f); 
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
	public abstract void setPIDAngle(double angle);
	
	/**
	 * Writes a power to the wheel that corresponds with module settings.
	 * @param power
	 */
	public void setPower(double power) {
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
	public void setValues(double angle, double power) {
		if (Math.abs(power) > 0.1) setRobotAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		setPower(power);
	}

	/**
	 * Directly Write the magnitude and power of a given angle to the module.
	 * Makes it very straightforward to write any calculated vector to the module.
	 * @param vector
	 */
	public void setVector(Vector vector) {
		setValues(vector.getAngle(), vector.getMagnitude());
	}
	
	/**
	 * write a speed that should be maintained to the controller
	 * @param speed ft/s
	 */
	public void setWheelSpeed(double speed) {
		speed *= DRIVE_WHEEL_FEET_TO_CLICK_RATIO;
		
		if (Math.abs(speed) > MAX_SPEED){
			System.out.println("Illegal speed " + speed + " written to module: " + moduleNumber);
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
		SmartDashboard.putNumber("SM_" + moduleNumber + "_WheelPower", driveWheelController.get());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_EncoderAngle", getEncoderAngle());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_RobotCentricAngle", robotCentricSetpointAngle);
	}
}
