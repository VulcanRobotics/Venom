package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * @author afiol-mahon
 */
public class AngleEncoder implements PIDSource {
	
	/*
	 *FIXME I believe issue with swerve is when robot passes an index during regular operation and calibrates, should only index when index command is called? maybe that will fix problem 
	 */
	
	private DigitalInput a;
	private DigitalInput b;
	private DigitalInput x;
	
	private Counter indexCounter;
	private Encoder encoder;

	private final double indexOffset;
	private double encoderOffset = 0;
	
	public AngleEncoder(int moduleNumber, double indexOffset) {
		this.a = new DigitalInput(RobotMap.SM_ANGLE_ENCODER_A[moduleNumber]);
		this.b = new DigitalInput(RobotMap.SM_ANGLE_ENCODER_B[moduleNumber]);
		this.x = new DigitalInput(RobotMap.SM_ANGLE_ENCODER_X[moduleNumber]);
		encoder = new Encoder(a , b);
		this.indexOffset = indexOffset;
		indexCounter = new Counter(x);
	}
	
	public void enableIndexing(boolean enabled) {
		if (enabled) {
			encoder.free();
 			encoder = new Encoder(a, b, x);
 			encoderOffset = indexOffset;
		} else {//FIXME disabling will not work correctly
			encoderOffset = indexOffset + encoder.get();
			encoder.free();
			encoder = new Encoder(a , b);
			encoder.reset();
		}
	}
	
	public boolean hasBeenIndexed() {
		return indexOffset != 0;
	}
	
	public double pidGet() {
		double angle = encoder.get() * SwerveModule.ANGLE_CONTROLLER_ENCODER_CLICK_TO_DEGREE;
		angle += encoderOffset;
		angle = Angle.get360Angle(angle);
		return angle;
	}
	
	public int getIndexCount() {
		return indexCounter.get();
	}
}