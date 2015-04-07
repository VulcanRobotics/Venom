package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * @author afiol-mahon
 */
public class AngleEncoder implements PIDSource {
	
	/*
	 *FIXME I believe issue with swerve is when robot passes an index during regular operation and calibrates, should only index when index command is called? maybe that will fix problem 
	 */
	
	DigitalInput a;
	DigitalInput b;
	DigitalInput x;
	
	private Counter indexCounter;
	private double moduleAngleOffset = 0;
	private Encoder encoder;
	private final double encoderOffeset;
	public AngleEncoder(int moduleNumber, double moduleAngleOffset) {
		a = new DigitalInput(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber]);
		b = new DigitalInput(RobotMap.SM_ANGLE_ENCODER_B[moduleNumber]);
		x = new DigitalInput(RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]);
		
		encoder = new Encoder(a , b);
		encoderOffeset = moduleAngleOffset;
		indexCounter = new Counter(x);
	}
	
	public void configureForIndexing(){
		System.out.println("configuring for index");
		encoder = new Encoder(a, b , x);
		this.moduleAngleOffset = encoderOffeset;
		System.out.println("done configuring for index");
	}
	
	public boolean hasBeenIndexed(){
		return moduleAngleOffset != 0;
	}
	
	public void configureForNoIndex(){
		System.out.println("configuring for no index");
		double oldPosition = encoder.get();
		encoder = new Encoder(a , b);
		encoder.reset();
		moduleAngleOffset = oldPosition;
		System.out.println("done configuring for no index");
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