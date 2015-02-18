package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.PIDController;
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
	
	private final CANTalon driveWheelController;
	//Drive Wheel Controller Constants
	private static final double DRIVE_WHEEL_RADIUS = 1.5; //inches
	private static final double DRIVE_WHEEL_CIRCUMFERENCE = (2.0 * Math.PI * DRIVE_WHEEL_RADIUS) / 12.0; //feet
	private static final double DRIVE_ENCODER_CLICKS_PER_REV = 500.0 * (6.0 / 5.0); //Encoder Clicks * Gearing Ratio
	private static final double DRIVE_ENCODER_CLICK_TO_FOOT = DRIVE_WHEEL_CIRCUMFERENCE / DRIVE_ENCODER_CLICKS_PER_REV;
	private static final double DRIVE_ENCODER_FOOT_TO_CLICK = DRIVE_ENCODER_CLICKS_PER_REV / DRIVE_WHEEL_CIRCUMFERENCE;
	
	//PID
	private static final double DRIVE_WHEEL_VELOCITY_P = 0.05;
	private static final double DRIVE_WHEEL_VELOCITY_I = 0.0;
	private static final double DRIVE_WHEEL_VELOCITY_D = 0.0;
	
	//Limits
	private static final double MAX_VELOCITY = 8.0; //feet per second
	private static final double DRIVE_POWER_SCALE = 0.5;
	
	private AngleEncoder angleEncoder;
	private final PIDController anglePIDController;
	private double moduleIndexOffset;

	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	private final CANTalon angleController;
	//Angle Controller Constants
	protected static final double MAX_ANGLE_CONTROLLER_POWER = 0.7;
	
	protected static final double ENCODER_CLICKS_PER_REVOLUTION = 500.0;
	protected static final double ENCODER_CLICK_TO_DEGREE = 360.0 / ENCODER_CLICKS_PER_REVOLUTION; //Degrees over Number of Clicks
	protected static final double ENCODER_DEGREE_TO_CLICK = ENCODER_CLICKS_PER_REVOLUTION / 360.0;

	private static final double ANGLE_CONTROLLER_DEGREE_TOLERANCE = 0;
	
	public SwerveModule(int moduleNumber, double moduleAngleOffset) {
		this.moduleNumber = moduleNumber;
		this.moduleIndexOffset = moduleAngleOffset;
		this.driveWheelController = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.initializeDriveWheelController();
		this.angleController = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleController.enableLimitSwitch(false, false);
		this.angleEncoder = new AngleEncoder(moduleNumber, moduleAngleOffset);
		this.anglePIDController = new PIDController(
			ANGLE_CONTROLLER_P,
			ANGLE_CONTROLLER_I,
			ANGLE_CONTROLLER_D,
			angleEncoder,
			angleController);
		this.anglePIDController.setInputRange(0.0, 360.0);
		this.anglePIDController.setOutputRange(-MAX_ANGLE_CONTROLLER_POWER, MAX_ANGLE_CONTROLLER_POWER);
		this.anglePIDController.setContinuous();
		this.anglePIDController.setAbsoluteTolerance(ANGLE_CONTROLLER_DEGREE_TOLERANCE);
		this.anglePIDController.onTarget();
		//Begin Module
		this.angleEncoder.reset();
		this.anglePIDController.enable();
	}
	
	private void initializeDriveWheelController() {
		this.driveWheelController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.driveWheelController.enableBrakeMode(false);
		this.driveWheelController.enableLimitSwitch(false, false);
		this.driveWheelController.enableForwardSoftLimit(false);
		this.driveWheelController.enableReverseSoftLimit(false);
		this.driveWheelController.setPosition(0);
		this.driveWheelController.setPID(DRIVE_WHEEL_VELOCITY_P, DRIVE_WHEEL_VELOCITY_I, DRIVE_WHEEL_VELOCITY_D);
	}
	
	public boolean isAnglePIDEnabled() {
		return anglePIDController.isEnable();
	}
	
	public void resetDistanceDriven() {
		driveWheelController.setPosition(0);
	}
	
	public void enableAnglePID(boolean enabled) {
		if (enabled) {
			anglePIDController.enable();
		} else {
			anglePIDController.disable();
		}
		
	}
	
	boolean isAngleCorrect() {
		return anglePIDController.onTarget();
	}
	
	public double getModuleIndexOffset() {
		return moduleIndexOffset;
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
	
	public double getDistance() {
		return (driveWheelController.getPosition() / 4) * DRIVE_ENCODER_CLICK_TO_FOOT;
	}
	
	/**
	 * Set the wheels to any angle that is relative to the robot's front.
	 * @param angle
	 */
	public void setAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.robotCentricSetpointAngle) > 90) invertModule = !invertModule;
		this.robotCentricSetpointAngle = angle;
		angle += (invertModule) ? 180 : 0;
		angle = 360 - angle;
		angle = Angle.get360Angle(angle);
		this.anglePIDController.setSetpoint(angle);
	}
	
	public void setAngleIndexingMode(boolean enabled) {
		if (enabled) {
			enableAnglePID(false);
			boolean invertTravelDirection = (Angle.diffBetweenAngles(getEncoderAngle(), -getModuleIndexOffset()) < 180 ? true : false);
    		angleController.set((invertTravelDirection) ? -0.8 : 0.8);
    		
		} else {
			enableAnglePID(true);
			setAngle(0.0);
		}
	}
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setAngleAndPower(double angle, double power) {
		if (Math.abs(power) > 0.1) setAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		setWheelPower(power);
	}
	
	public void setAngleAndVelocity(double angle, double percentSpeed) {
		if (Math.abs(percentSpeed) > 0.1) {
			setAngle(angle);
			setWheelVelocity(percentSpeed * MAX_VELOCITY);
		} else {
			setWheelPower(0.0);
		}
	}
	
	public void setAngleAndPower(Vector vector) {
		setAngleAndPower(vector.getAngle(), vector.getMagnitude());
	}
	
	public void setAngleAndVelocity(Vector vector) {
		setAngleAndVelocity(vector.getAngle(), vector.getMagnitude());
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
	 * write a speed that should be maintained to the controller
	 * @param speed ft/s
	 */
	public void setWheelVelocity(double speed) {
		if (Math.abs(speed) > MAX_VELOCITY) {
			System.out.println("Illegal speed " + speed + "(ft/s) written to module: " + moduleNumber);
		} else {
			speed *= DRIVE_ENCODER_FOOT_TO_CLICK;
			speed *= (invertModule) ? -1.0 : 1.0;
			this.driveWheelController.changeControlMode(ControlMode.Speed);
			this.driveWheelController.set(speed * 10); //Multiply by 10 because PID controller takes Units per decisecond
		}
	}
	
	/**
	 * Update the dashboard with current module information.
	 */
	public void syncDashboard() {
		String prefix = "SM_"+ moduleNumber + "_";
		SmartDashboard.putNumber(prefix + "WheelPower", driveWheelController.get());
		SmartDashboard.putNumber(prefix + "EncoderAngle", getEncoderAngle());
		SmartDashboard.putNumber(prefix + "RobotCentricAngle", robotCentricSetpointAngle);
		SmartDashboard.putNumber(prefix + "IndexCount", getEncoderIndexCount());
		SmartDashboard.putNumber(prefix + "DistanceDriven", driveWheelController.getPosition() * DRIVE_ENCODER_CLICK_TO_FOOT);
	}
}
