package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;

/**
 * @author afiol-mahon
 */
public class AngleEncoder extends Encoder {
	
	private Counter indexCounter;
	private double moduleAngleOffset;
	
	private int moduleNumber;
	
	public AngleEncoder(int moduleNumber, double moduleAngleOffset) {
		this.moduleNumber = moduleNumber;
		super(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber],
				RobotMap.SM_ANGLE_ENCODER_B[moduleNumber],
				RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]
				);
		this.moduleAngleOffset = moduleAngleOffset;
		indexCounter = new Counter(this.m_indexSource);
	}
	
	public double pidGet() {
		double angle = get() * SwerveModule.ANGLE_CONTROLLER_ENCODER_CLICK_TO_DEGREE;
		angle += moduleAngleOffset;
		angle = Angle.get360Angle(angle);
		return angle;
	}
	
	public void enableAutoIndex() {
		super.setIndexSource(RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]);
	}
	
	public void disableAutoIndex(){
		//stops indexing by using unused pin as index pin -janky fix
		super.setIndexSource(RobotMap.UNUSED_PIN);
	}
	
	public void resetIndexes(){
		indexCounter.reset();
	}
	
	public int getIndexCount() {
		return indexCounter.get();
	}
}