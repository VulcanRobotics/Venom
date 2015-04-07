package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * @author afiol-mahon
 */
public class AngleEncoder implements PIDSource {
	
	/*
	 *FIXME I believe issue with swerve is when robot passes an index during regular operation and calibrates, should only index when index command is called? maybe that will fix problem 
	 */
	
	
	private Counter indexCounter;
	private double moduleAngleOffset = 0;
	private Encoder encoder;
	private final double encoderOffeset;
	public AngleEncoder(int moduleNumber, double moduleAngleOffset) {
		encoder = new Encoder(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber], RobotMap.SM_ANGLE_ENCODER_B[moduleNumber]);
		encoderOffeset = moduleAngleOffset;
		indexCounter = new Counter(this.m_indexSource);
	}
	
	public void configureForIndexing(){
		encoder = new Encoder(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber],
				RobotMap.SM_ANGLE_ENCODER_B[moduleNumber],
				RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]);
		this.moduleAngleOffset = encoderOffeset;
		encoder.start();
	}
	
	public void configureForNoIndex(){
		double oldPosition = encoder.get();
		encoder = new Encoder(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber], RobotMap.SM_ANGLE_ENCODER_B[moduleNumber]);
		encoder.reset();
		encoder.start();
		moduleAngleOffset = oldPosition;
	}
	
	public double pidGet() {
		double angle = encoder.get() * SwerveModule.ANGLE_CONTROLLER_ENCODER_CLICK_TO_DEGREE;
		angle += moduleAngleOffset;
		angle = Angle.get360Angle(angle);
		return angle;
	}
	
	public int getIndexCount() {
		return indexCounter.get();
	}
}