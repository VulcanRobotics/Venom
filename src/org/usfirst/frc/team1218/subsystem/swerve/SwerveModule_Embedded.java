package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * @author afiol-mahon
 */
public class SwerveModule_Embedded extends SwerveModule {
		
	private static final double ANGLE_CONTROLLER_P = 0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	public SwerveModule_Embedded(int moduleNumber) {
		super(moduleNumber);
		this.angleController.changeControlMode(CANTalon.ControlMode.Position);
		this.angleController.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		this.angleController.reverseSensor(MODULE_REVERSED[moduleNumber]);
		Scheduler.getInstance().add(new C_IndexPeriodic(this)); //Begin a command to monitor the encoder index pin
		this.angleController.setPID(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D);
		this.angleController.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 1);//XXX Running this really fast check CANBus Utilization
		this.angleController.setPosition(0);
	}
	
	@Override
	public void setRealAngle(double angle) {
		angle *= ENCODER_DEGREE_TO_CLICK;
		angle = Angle.get360Angle(angle);
		this.angleController.set(angle);
	}
	
	@Override
	public double getEncoderAngle() {
		double position = this.angleController.getEncPosition();
		position *=ENCODER_CLICK_TO_DEGREE;
		position = Angle.get360Angle(position);
		return position;
	}
	
	private int encoderIndexRisingEdges = 0;
	protected void zeroEncoderOnIndex() {
		int currentEncRisingEdges = angleController.getNumberOfQuadIdxRises();
		if (currentEncRisingEdges > encoderIndexRisingEdges) {
			angleController.setPosition(0);
			encoderIndexRisingEdges = currentEncRisingEdges;
		}
	}
	
	/**
	 * Command functions as an event handling loop for the index pin
	 * @author afiol-mahon
	 */
	public class C_IndexPeriodic extends Command {
		
		private SwerveModule_Embedded module;
		
		public C_IndexPeriodic(SwerveModule_Embedded module) {
			this.module = module;
		}
		
		@Override
		protected void initialize() {}

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
			System.out.println("Warning! Index Watch for SM_" + moduleNumber + " has ended");
		}

		@Override
		protected void interrupted() {
			end();
		}
	}
}