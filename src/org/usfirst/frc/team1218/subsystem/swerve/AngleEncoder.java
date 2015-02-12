package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;

public class AngleEncoder extends Encoder {
	
	/**
	 * 
	 */
	private Counter indexCounter;
	private int moduleNumber;
	
	public AngleEncoder(int moduleNumber) {
		super(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber],
				RobotMap.SM_ANGLE_ENCODER_B[moduleNumber],
				RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]
				);
		this.moduleNumber = moduleNumber;
		indexCounter = new Counter(this.m_indexSource);
	}
	
	@Override
	public double pidGet() {
		double angle = get() * SwerveModule.ENCODER_CLICK_TO_DEGREE;
		angle += SwerveModule.MODULE_ANGLE_OFFSET[moduleNumber];
		angle = Angle.get360Angle(angle);
		return angle;
	}
	
	public int getIndexCount() {
		return indexCounter.get();
	}
}