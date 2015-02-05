package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Swerve Class that handles all module logic except for the angle writing itself
 * @author afiol-mahon
 */
public abstract class SwerveModule {
	
	protected final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	protected double robotCentricAngle = 0; //Angle relative to front of robot
	
	//Module Runtime Settings
	private boolean invertModule = false;
	private boolean stableMode = false;
	
	protected final CANTalon driveWheelController;
	private static final double DRIVE_POWER_SCALE = 0.2;
	protected static final boolean[] MODULE_REVERSED = {false, false, true, true};
	
	protected final CANTalon angleController;
	protected static final double MAX_ANGLE_CONTROLLER_POWER = 1.0;
	
	private static final double ENCODER_CLICKS_PER_REVOLUTION = 500.0;
	protected static final double ENCODER_CLICK_TO_DEGREE = 360.0 / ENCODER_CLICKS_PER_REVOLUTION; //Degrees over Number of Clicks
	protected static final double ENCODER_DEGREE_TO_CLICK = ENCODER_CLICKS_PER_REVOLUTION / 360.0;
	protected static final double ENCODER_FEET_PER_CLICK = 0; //TODO Get Value
	
	
	private static final double NORMAL_MODE_RAMP_RATE = 0;
	private static final double STABLE_MODE_RAMP_RATE = 0;
	
	public SwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveWheelController = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.driveWheelController.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
		this.driveWheelController.enableBrakeMode(false);
		this.driveWheelController.enableLimitSwitch(false, false);
		this.driveWheelController.enableForwardSoftLimit(false);
		this.driveWheelController.enableReverseSoftLimit(false);
		this.driveWheelController.setExpiration(100);
		this.driveWheelController.setSafetyEnabled(true);
		this.angleController = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
	}
	
	/**
	 * @return true if the module is currently operating in stable mode.
	 */
	public boolean getStableMode() {
		return stableMode;
	}
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setValues(double angle, double power) {
		if (Math.abs(power) > 0.1) writeRobotCentricAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
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
	
	public double getVelocity() { //TODO write
		return 0;
	}
	
	public double getDistance() { //TODO write
		return 0;
	}
	
	/**
	 * Get the value of the encoder currently used on the angle
	 * @returns degree position of encoder
	 */
	public abstract double getEncoderAngle();
	
	/**
	 * Method that controls final stage of setting a properly offset angle
	 * to the swerve drive.
	 * The purpose of this is to allow the swerveModule to handle as much of
	 * the uniform calculations that happen in both modules as possible
	 * so that both SwerveModule Classes are as small as possible
	 * so they just have to define the last part of the angle write
	 * @param angle Desired wheel angle. Can be any value
	 */
	public abstract void setRealAngle(double angle);
	
	/**
	 * Writes a power to the wheel that corresponds with module settings.
	 * @param power
	 */
	public void setPower(double power) {
		if (Math.abs(power) > 1){
			System.out.println("Illegal power " + power + " written to module: " + moduleNumber);
		} else {
			power *= (invertModule) ? -1.0 : 1.0;
			this.driveWheelController.set(DRIVE_POWER_SCALE * power * ((MODULE_REVERSED[moduleNumber]) ? 1.0 : -1.0)); //Applies module specific motor preferences
		}
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
	 * Update the dashboard with current module information.
	 */
	public void syncDashboard() {//TODO fix driver station value keys
		SmartDashboard.putNumber("SM_" + moduleNumber + "_WheelPower", driveWheelController.get());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_EncoderAngle", getEncoderAngle());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_RobotCentricAngle", robotCentricAngle);
	}
	
	/**
	 * Set the wheels to any angle that is relative to the robot's front.
	 * @param angle
	 */
	public void writeRobotCentricAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.robotCentricAngle) > 90) invertModule = !invertModule;
		this.robotCentricAngle = angle;
		angle += (invertModule) ? 180 : 0;
		angle = (MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle;
		angle = Angle.get360Angle(angle);
		setRealAngle(angle);
	}
}
