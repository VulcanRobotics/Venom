package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.Angle;
import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author afiol-mahon
 */
public class VulcanSwerveModule extends Object {
	
	protected final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	private final CANTalon angleMotor;
	private AngleEncoder angleEncoder;
	private static final double ENCODER_CLICK_DEGREE_RATIO = 360.0 / 500; //Degrees over Number of Clicks

	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	private static final double ANGLE_MOTOR_OUTPUT_RANGE = 1.0;
	
	private static final boolean[] MODULE_REVERSED = {false, false, true, true};
	
	private boolean invertModule = false;
	private double angle = 0; //Current module angle
	
	private final CANTalon driveMotor;
	private static final double DRIVE_POWER_SCALE = 0.4;
	
	public VulcanSwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveMotor = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
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
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setValues(double angle, double power) {
		if (Math.abs(power) > 0.1) setAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		setPower(power);
	}
	
	public void setVector(Vector vector) {
		setValues(vector.getAngle(), vector.getMagnitude());
	}
	
	/**
	 * angleController setpoint should always be set through this method in order to apply zeroing offsets
	 * @param angle Desired wheel angle. Can be any value
	 */
	public void setAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.angle) > 90) invertModule = !invertModule;
		this.angle = angle;
		angle += (invertModule) ? 180 : 0;
		this.angleController.setSetpoint(Angle.get360Angle(((MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle))); //applies module specific direction preferences
	}
	
	public void setPower(double power) {
		if (Math.abs(power) > 1){
			System.out.println("Illegal power " + power + " written to module: " + moduleNumber);
		} else {
			power *= (invertModule) ? -1.0 : 1.0;
			this.driveMotor.set(DRIVE_POWER_SCALE * power * ((MODULE_REVERSED[moduleNumber]) ? 1.0 : -1.0)); //Applies module specific motor preferences
		}
	}
	
	public void syncDashboard() {//TODO fix driver station value keys
		SmartDashboard.putNumber("SM_" + moduleNumber + "_Encoder", angleEncoder.pidGet());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_WheelPower", driveMotor.get());
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
			return Angle.get360Angle(get() * ENCODER_CLICK_DEGREE_RATIO);
		}
	}
}