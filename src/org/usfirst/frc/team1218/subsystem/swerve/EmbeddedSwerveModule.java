package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author afiol-mahon
 */
public class EmbeddedSwerveModule extends Object {
	
	protected final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	private final CANTalon angleMotor;
	
	private static final double DEGREES = 360.0;
	private static final double ENCODER_CLICKS = 500.0;
	private static final double ENCODER_CLICKS_PER_DEGREE = ENCODER_CLICKS / DEGREES;
	private static final double DEGREES_PER_ENCODER_CLICK = DEGREES / ENCODER_CLICKS; //Degrees over Number of Clicks

	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	private static final boolean[] MODULE_REVERSED = {false, false, true, true};
	
	private boolean invertModule = false; //Decides which side of the wheel to use as the "front" side
	private double robotCentricAngle = 0; //Current module angle orientation (0-360)
	
	private final CANTalon driveMotor;
	private static final double DRIVE_POWER_SCALE = 0.4;
	
	public EmbeddedSwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveMotor = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		this.angleMotor = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleMotor.changeControlMode(CANTalon.ControlMode.Position);
		this.angleMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		this.angleMotor.reverseSensor(MODULE_REVERSED[moduleNumber]);
		this.angleMotor.setPID(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D);
		this.angleMotor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 1);//XXX Running this really fast check CANBus Utilization
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
		if (Angle.diffBetweenAngles(angle, this.robotCentricAngle) > 90) {
			invertModule = !invertModule;
		}
		this.robotCentricAngle = angle;
		angle += (invertModule) ? 180 : 0; //Inverts Direction if module is running inverted
		angle = (MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle; //Makes the module run counterclockwise
		angle = Angle.get360Angle(angle); //Coerce angle to 0-360 range
		setModuleAngle(angle);
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
		SmartDashboard.putNumber("SM_" + moduleNumber + "_Encoder", getEncoderAngle(this.angleMotor.getEncPosition()));
		SmartDashboard.putNumber("SM_" + moduleNumber + "_WheelPower", driveMotor.get());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_AngleSetpoint", robotCentricAngle);
	}
	
	private double getEncoderAngle(double encoderValue) {
		double position = encoderValue * DEGREES_PER_ENCODER_CLICK;
		position = Angle.get360Angle(position);
		return position;
	}
	
	private void setModuleAngle(double angle) {
		this.angleMotor.set(Angle.get360Angle(angle * ENCODER_CLICKS_PER_DEGREE));
	}
	
	private int encoderRisingEdges = 0;
	protected void zeroEncoderOnIndex() {
		int currentEncRisingEdges = angleMotor.getNumberOfQuadIdxRises();
		if (currentEncRisingEdges > encoderRisingEdges) {
			angleMotor.setPosition(0);
			encoderRisingEdges = currentEncRisingEdges;
		}
 	}
	
}