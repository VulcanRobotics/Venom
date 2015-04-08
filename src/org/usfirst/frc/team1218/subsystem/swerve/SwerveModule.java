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
 * Swerve Class that handles all module specific logic
 * @author afiol-mahon
 */
public class SwerveModule {
	
	double MAX_GROUNDED_VELOCITY = 10;
	
	boolean shouldDiscount = false;
	public final int moduleNumber;
	private final double moduleIndexOffset;
	
	private boolean invertModule = false;
	private double robotCentricSetpointAngle = 0;
	
	private final CANTalon driveWheelController;
	private final CANTalon angleController;
	private final AngleEncoder angleEncoder;
	private final PIDController anglePIDController;
	
	protected static final double DRIVE_WHEEL_RADIUS = 1.5; //inches
	protected static final double DRIVE_WHEEL_CIRCUMFERENCE = (2.0 * Math.PI * DRIVE_WHEEL_RADIUS) / 12.0; //feet
	protected static final double DRIVE_WHEEL_ENCODER_CLICKS_PER_REV = 500.0 * (6.0 / 5.0); //Encoder Clicks * Gearing Ratio
	protected static final double DRIVE_WHEEL_ENCODER_CLICK_TO_FOOT = DRIVE_WHEEL_CIRCUMFERENCE / DRIVE_WHEEL_ENCODER_CLICKS_PER_REV;
	protected static final double DRIVE_WHEEL_ENCODER_FOOT_TO_CLICK = DRIVE_WHEEL_ENCODER_CLICKS_PER_REV / DRIVE_WHEEL_CIRCUMFERENCE;
	
	protected static final double DRIVE_WHEEL_VELOCITY_P = 0.05;
	protected static final double DRIVE_WHEEL_VELOCITY_I = 0.0;
	protected static final double DRIVE_WHEEL_VELOCITY_D = 0.0;
	
	protected static final double DRIVE_WHEEL_MAX_VELOCITY = 8.0; //feet per second

	protected static final double ANGLE_CONTROLLER_P = -0.02;
	protected static final double ANGLE_CONTROLLER_I = -0.0005;
	protected static final double ANGLE_CONTROLLER_D = 0.0;
	
	protected static final double ANGLE_CONTROLLER_ENCODER_CLICKS_PER_REVOLUTION = 500.0;
	protected static final double ANGLE_CONTROLLER_ENCODER_CLICK_TO_DEGREE = 360.0 / ANGLE_CONTROLLER_ENCODER_CLICKS_PER_REVOLUTION; //Degrees over Number of Clicks
	protected static final double ANGLE_CONTROLLER_ENCODER_DEGREE_TO_CLICK = ANGLE_CONTROLLER_ENCODER_CLICKS_PER_REVOLUTION / 360.0;
	
	protected static final double ANGLE_CONTROLLER_MAX_POWER = 0.7;
	protected static final double ANGLE_CONTROLLER_DEGREE_TOLERANCE = 30;
	
	public SwerveModule(int moduleNumber, double moduleAngleOffset) {
		this.moduleNumber = moduleNumber;
		this.moduleIndexOffset = moduleAngleOffset;
		this.driveWheelController = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.initializeDriveWheelController();
		this.angleController = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleController.enableBrakeMode(true);
		this.angleController.enableLimitSwitch(false, false);
		this.angleEncoder = new AngleEncoder(moduleNumber, moduleAngleOffset);
		this.anglePIDController = new PIDController(
			ANGLE_CONTROLLER_P,
			ANGLE_CONTROLLER_I,
			ANGLE_CONTROLLER_D,
			angleEncoder,
			angleController);
		this.anglePIDController.setInputRange(0.0, 360.0);
		this.anglePIDController.setOutputRange(-ANGLE_CONTROLLER_MAX_POWER, ANGLE_CONTROLLER_MAX_POWER);
		this.anglePIDController.setContinuous();
		this.anglePIDController.setAbsoluteTolerance(ANGLE_CONTROLLER_DEGREE_TOLERANCE);
		this.anglePIDController.onTarget();
		//Begin Module
		this.anglePIDController.enable();
	}
	
	private void initializeDriveWheelController() {
		this.driveWheelController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.driveWheelController.enableBrakeMode(true);
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
		shouldDiscount = false;
		driveWheelController.setPosition(0);
	}
	
	public void enableAnglePID(boolean enabled) {
		if (enabled) {
			anglePIDController.enable();
			setAngle(0.0);
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
	
	/**
	 * Current module angle from encoder with respect to front of robot
	 * @return Current robotCentricAngle
	 */
	public double getModuleAngle() {
		double angle = getEncoderAngle();
		angle += invertModule ? 180 : 0;
		angle = 360 - angle;
		angle = Angle.get360Angle(angle);
		return angle;
	}

	public int getEncoderIndexCount() {
		return angleEncoder.getIndexCount();
	}
	
	public double getAbsoluteDistanceDriven() {
		double distance = Math.abs((driveWheelController.getPosition() / 4.0) * DRIVE_WHEEL_ENCODER_CLICK_TO_FOOT);
		if (getVelocity() > MAX_GROUNDED_VELOCITY) {
			shouldDiscount = true;
		}
		if (shouldDiscount){
			distance = 0;
		}
		return distance;
	}
	
	public void enableIndexing(boolean enabled) {
		angleEncoder.enableIndexing(enabled);
		System.out.println("SM_" + moduleNumber + " encoder indexing set to " + enabled);
	}
	
	public boolean hasBeenIndexed(){
		return angleEncoder.hasBeenIndexed();
	}
	
	public void faceForward(){
		this.anglePIDController.setSetpoint(0);
	}
	
	public double getVelocity() {
		return (driveWheelController.getEncVelocity() / 4.0) * DRIVE_WHEEL_ENCODER_CLICK_TO_FOOT / 10.0; //div by 4 to convert from 4X encoding to clicks and divide by 10 to convert deciseconds to seconds
	}
	
	public double getDriveCurrent() {
		return driveWheelController.getOutputCurrent();
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
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setAngleAndPower(double angle, double power) {
		if (Math.abs(power) > 0.1) {
			setAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
			setWheelPower(power);
		} else {
			setWheelPower(0.0);
		}
	}
	
	/**
	 * @param angle
	 * @param percentSpeed
	 */
	public void setAngleAndVelocity(double angle, double percentSpeed) {
		if (Math.abs(percentSpeed) > 0.1) {
			setAngle(angle);
			setWheelVelocity(percentSpeed * DRIVE_WHEEL_MAX_VELOCITY);
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
	
	public void setPowerToAngleMotor(double power) {
		angleController.set(power);
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
			this.driveWheelController.set(power);
		}
	}
	
	/**
	 * write a speed that should be maintained to the controller
	 * @param speed ft/s
	 */
	public void setWheelVelocity(double speed) {
		if (Math.abs(speed) > DRIVE_WHEEL_MAX_VELOCITY) {
			System.out.println("Illegal speed " + speed + "(ft/s) written to module: " + moduleNumber);
		} else {
			speed *= DRIVE_WHEEL_ENCODER_FOOT_TO_CLICK;
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
		SmartDashboard.putNumber(prefix + "RobotCentricSetpointAngle", Angle.get360Angle(robotCentricSetpointAngle));
		SmartDashboard.putNumber(prefix + "RobotCentricCurrentAngle", getModuleAngle());
		SmartDashboard.putNumber(prefix + "IndexCount", getEncoderIndexCount());
		SmartDashboard.putNumber(prefix + "DistanceDriven", getAbsoluteDistanceDriven());
		SmartDashboard.putNumber(prefix + "DriveCurrent", getDriveCurrent());
		SmartDashboard.putNumber(prefix + "Speed", getVelocity());
	}
}
