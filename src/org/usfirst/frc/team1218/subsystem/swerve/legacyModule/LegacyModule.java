package org.usfirst.frc.team1218.subsystem.swerve.legacyModule;

import org.usfirst.frc.team1218.math.Angle;
import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.VulcanSwerveModule;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LegacyModule extends VulcanSwerveModule {
	
	private final DigitalInput zeroSensor;
	
	private boolean isZeroing = false;
		
	private static final double RESET_TURN_POWER = 0.25;
	
	private static final double[] MODULE_ANGLE_OFFSET = {40.0, -36.0, -22.0, 85.0};
	
	AngleEncoder angleEncoder;
	
	
	public LegacyModule(int moduleNumber) {
		super(moduleNumber);
		this.zeroSensor = new DigitalInput(RobotMap.SM_ZERO[moduleNumber]);
		this.angleEncoder = new AngleEncoder(moduleNumber);
	}
	
	@Override
	public void setAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.angle) > 90) {
			invertModule = !invertModule;
		}
		this.angle = angle;
		angle += (invertModule) ? 180 : 0;
		angle += MODULE_ANGLE_OFFSET[moduleNumber]; //adds angle zeroing point offset to the modules written angle.
		angle = (MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle;
		angle = Angle.get360Angle(angle);
		displayAngle = angle;
		this.angleController.setSetpoint(angle); //applies module specific direction preferences
	}
	
	@Override
	public void setZeroing() {
		this.isZeroing = true;
	}
	
	@Override
	public boolean getZeroing() {
		return this.isZeroing;
	}
	
	@Override
	public void zeroModule() {
		this.driveMotor.set(0.0);
		if (zeroSensor.get()) {
			//At Zero
			this.angleEncoder.reset();
			this.angleMotor.set(0.0);
			this.setAngle(0.0);
			this.angleController.enable();
			this.isZeroing = false;
		}else {
			//Finding Zero
			this.angleController.disable();
			this.angleMotor.set(RESET_TURN_POWER);
			this.setZeroing();
		}
	}
	
	@Override
	public void publishValues() {
		super.publishValues();
		SmartDashboard.putBoolean("SM" + moduleNumber + "_isZeroing", isZeroing);
		SmartDashboard.putBoolean("SM" + moduleNumber + "_ZeroSensor", zeroSensor.get());
	}
	
	public class AngleEncoder extends Encoder {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		private static final double ENCODER_CLICK_DEGREE_RATIO = (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO;
		
		public AngleEncoder(int moduleNumber) {
			super(RobotMap.SM_ENCODER_A[moduleNumber],
					RobotMap.SM_ENCODER_B[moduleNumber],
					RobotMap.SM_ENCODER_I[moduleNumber],
					MODULE_REVERSED[moduleNumber]
					);
		}
		
		@Override
		public double pidGet() {
			return Angle.get360Angle(get() * ENCODER_CLICK_DEGREE_RATIO);
		}
	}
}
