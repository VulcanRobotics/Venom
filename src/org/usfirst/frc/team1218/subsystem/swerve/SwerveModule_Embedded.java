package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.StatusFrameRate;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class SwerveModule_Embedded extends SwerveModule {
		
	private double ANGLE_CONTROLLER_P = 0.8;
	private double ANGLE_CONTROLLER_I = 0.002;
	private double ANGLE_CONTROLLER_D = 0.0;
	private int QUAD_ENCODER_UPDATE_FRAME_RATE = 3;
	
	private IndexTrigger indexTrigger;
	
	private double encoderClickPosition = 0;
	
	public SwerveModule_Embedded(int moduleNumber) {
		super(moduleNumber);
		this.angleController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.angleController.changeControlMode(ControlMode.Position);
		this.angleController.setPID(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D);
		this.angleController.setStatusFrameRateMs(StatusFrameRate.QuadEncoder, QUAD_ENCODER_UPDATE_FRAME_RATE);
		this.angleController.reverseSensor(false);
		indexTrigger = new IndexTrigger();
		indexTrigger.whenActive(new IndexModule());
	}
	
	@Override
	public void setPIDAngle(double angle) {
		encoderClickPosition += this.angleController.getEncPosition();
		this.angleController.setPosition(0);
		
		angle = Angle.get360Angle(angle);
		angle *= ENCODER_DEGREE_TO_CLICK;
		
		double error = angle - encoderClickPosition;
		double maxInput = ENCODER_CLICKS_PER_REVOLUTION;
		double minInput = 0;
		//Continuous Code from WPI Library PIDController Class
		if (Math.abs(error) > (maxInput - minInput) / 2) {
            if (error > 0) {
                error = error - maxInput + minInput;
            } else {
                error = error + maxInput - minInput;
            }
        }
		this.angleController.set(error);
	}
	
	@Override
	public double getEncoderAngle() {
		return encoderClickPosition * ENCODER_CLICK_TO_DEGREE;
	}
	
	private int lastIndexCount = -1;
	
	private boolean hasHitIndex() {
		int currentIndexCount = angleController.getNumberOfQuadIdxRises();
		if (currentIndexCount != lastIndexCount) {
			lastIndexCount = currentIndexCount;
			System.out.println("SM_" + moduleNumber + ": Index Hit");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void syncDashboard() {
		super.syncDashboard();
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Angle_Controller_Output_Voltage", angleController.getOutputVoltage());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Index_Edges_Counted", angleController.getNumberOfQuadIdxRises());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Angle_Controller_PID_Error", angleController.getClosedLoopError());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Encoder_Click_Position_Relative", angleController.getPosition());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Encoder_Click_Position_True", encoderClickPosition);
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Click_Setpoint", angleController.getSetpoint());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Angle_Controller_P_Value", angleController.getP());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Angle_Controller_I_Value", angleController.getI());
		//SmartDashboard.putNumber("SM_" + moduleNumber + "_Angle_Controller_D_Value", angleController.getD());
	}
	
	class IndexTrigger extends Trigger {
		@Override
		public boolean get() {
			return hasHitIndex();
		}
	}
	
	class IndexModule extends Command {
		
		@Override
		protected void initialize() {
			encoderClickPosition = -1.0 * (MODULE_ANGLE_OFFSET[moduleNumber] * ENCODER_DEGREE_TO_CLICK);
			//FIXME try adding if this doesn't work initially
			//angleController.setPosition(0);
			System.out.println("SM_" + moduleNumber + ": Reset");
		}
		
		@Override
		protected void execute() {}
		
		@Override
		protected boolean isFinished() {
			return true;
		}

		@Override
		protected void end() {}
		@Override
		protected void interrupted() {}
	}
}