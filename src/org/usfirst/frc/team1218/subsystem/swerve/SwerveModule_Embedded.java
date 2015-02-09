package org.usfirst.frc.team1218.subsystem.swerve;

import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.StatusFrameRate;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author afiol-mahon
 */
public class SwerveModule_Embedded extends SwerveModule {
		
	private static final double ANGLE_CONTROLLER_P = 0.1;
	private static final double ANGLE_CONTROLLER_I = 0.1;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	
	public SwerveModule_Embedded(int moduleNumber) {
		super(moduleNumber);
		this.angleController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.angleController.changeControlMode(ControlMode.Position);
		this.angleController.setPID(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D);
		this.angleController.setStatusFrameRateMs(StatusFrameRate.QuadEncoder, 3);
		this.angleController.set(0);
		//new IndexSystem(this);
	}
	
	@Override
	public void setRealAngle(double angle) {
		angle *= ENCODER_DEGREE_TO_CLICK;
		this.angleController.set(angle);
	}
	
	@Override
	public double getEncoderAngle() {
		double position = this.angleController.getEncPosition();
		position *= ENCODER_CLICK_TO_DEGREE;
		//position = Angle.get360Angle(position);
		return position;
	}
	
	private int lastIndexCount = -1;
	protected void zeroEncoderOnIndex() {
		int currentIndexCount = angleController.getNumberOfQuadIdxRises();
		if (currentIndexCount != lastIndexCount) {
			angleController.setPosition(SwerveModule.MODULE_ANGLE_OFFSET[moduleNumber]);
			lastIndexCount = currentIndexCount;
			System.out.println("INDEX OF SM_" + moduleNumber + ": Reset");
		}
	}
	@Override
	public void syncDashboard() {
		super.syncDashboard();
		SmartDashboard.putNumber("SM_" + moduleNumber + "_AngleError", angleController.getClosedLoopError());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_AngleMotorVoltage", angleController.getOutputVoltage());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_QuadCount", angleController.getNumberOfQuadIdxRises());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_Raw_Position", angleController.getPosition());
		SmartDashboard.putNumber("SM_" + moduleNumber + "_Raw_Setpoint", angleController.getSetpoint());
		angleController.setPID(10.0, 0.0, 0.0001);
	}
	
	/**
	 * Command functions as an event handling loop for the index pin
	 * @author afiol-mahon
	 */
	public class IndexSystem extends Subsystem{
		
		SwerveModule_Embedded module;
		
		public IndexSystem(SwerveModule_Embedded module) {
			this.module = module;
		}
		
		@Override
		protected void initDefaultCommand() {
			setDefaultCommand(new C_IndexPeriodic(module, this));
		}
		class C_IndexPeriodic extends Command{
			SwerveModule_Embedded module;
			public C_IndexPeriodic(SwerveModule_Embedded module, Subsystem sub) {
				this.module = module;
				requires(sub);
			}
			
			@Override
			protected void initialize() {
				System.out.println("Index Watch " + module.moduleNumber + " has started");
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
				System.out.println("Warning: Index watcher " + module.moduleNumber + " has been terminated.");
			}

			@Override
			protected void interrupted() {
				end();
			}
			
		}
	}
}