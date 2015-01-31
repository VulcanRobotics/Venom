package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author afiol-mahon
 */
public class EmbeddedSwerveModule extends SwerveModule {
	
	private final CANTalon angleMotor;
	
	private static final double DEGREES = 360.0;
	private static final double ENCODER_CLICKS = 500.0;
	private static final double ENCODER_CLICKS_PER_DEGREE = ENCODER_CLICKS / DEGREES;
	private static final double DEGREES_PER_ENCODER_CLICK = DEGREES / ENCODER_CLICKS; //Degrees over Number of Clicks

	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	public EmbeddedSwerveModule(int moduleNumber) {
		super(moduleNumber);
		this.angleMotor = new CANTalon(RobotMap.SM_TURN_MOTOR[moduleNumber]);
		this.angleMotor.changeControlMode(CANTalon.ControlMode.Position);
		this.angleMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		this.angleMotor.reverseSensor(MODULE_REVERSED[moduleNumber]);
		Scheduler.getInstance().add(new C_IndexPeriodic(this)); //Begin a command to monitor the encoder index pin
		this.angleMotor.setPID(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D);
		this.angleMotor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 1);//XXX Running this really fast check CANBus Utilization
	}
	
	@Override
	public void syncDashboard() {
		super.syncDashboard();
		SmartDashboard.putNumber("SM_" + moduleNumber + "_Encoder", getEncoderAngle(this.angleMotor.getEncPosition()));
		SmartDashboard.putNumber("SM_" + moduleNumber + "_AngleSetpoint", robotCentricAngle);
	}
	
	@Override
	public void setRealAngle(double angle) {
		angle *= ENCODER_CLICKS_PER_DEGREE;
		angle = Angle.get360Angle(angle);
		this.angleMotor.set(angle);
	}
	
	private double getEncoderAngle(double encoderValue) {
		double position = encoderValue * DEGREES_PER_ENCODER_CLICK;
		position = Angle.get360Angle(position);
		return position;
	}
	
	private int encoderRisingEdges = 0;
	protected void zeroEncoderOnIndex() {
		int currentEncRisingEdges = angleMotor.getNumberOfQuadIdxRises();
		if (currentEncRisingEdges > encoderRisingEdges) {
			angleMotor.setPosition(0);
			encoderRisingEdges = currentEncRisingEdges;
		}
	}
	
	/**
	 * Command functions as an event handling loop for the index pin
	 * @author afiol-mahon
	 */
	public class C_IndexPeriodic extends Command {
		
		private EmbeddedSwerveModule module;
		
		public C_IndexPeriodic(EmbeddedSwerveModule module) {
			this.module = module;
		}
		
		@Override
		protected void initialize() {
		}

		@Override
		protected void execute() {
			module.zeroEncoderOnIndex();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}

		@Override
		protected void end() {
			SmartDashboard.putBoolean("SM_" + module.moduleNumber + " Index Watch Command Falure Alert", true);
		}

		@Override
		protected void interrupted() {
			end();
		}

	}
}