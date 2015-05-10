package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Wrapper class for the encoder that provides swerve module specific encoder output and utility
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
	
	/**
	 * If manual wheel alignment at start of match is compromised, this method will revert to reading the index to calibrate during operation
	 */
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
	
	/**
	 * @return true if module has indexed before
	 */
	public boolean hasBeenIndexed() {
		return indexOffset != 0;
	}
	
	/**
	 * returns the angle of the swerve module from 0-360, 0 and 360 both representing forward on the robot
	 */
	public double pidGet() {
		double angle = encoder.get() * SwerveModule.ANGLE_CONTROLLER_ENCODER_CLICK_TO_DEGREE;
		angle += encoderOffset;
		angle = Angle.get360Angle(angle);
		return angle;
	}
	
	/**
	 * Calibrate module encoder at an offset
	 * @param offset
	 */
	public void setInitialOffset(double offset) {
		encoder.reset();
		this.encoderOffset = -offset; //if starting at 10, then subtract 10 from all future encoder readings
	}
	
	/**
	 * @return number of times the module has seen the index
	 */
	public int getIndexCount() {
		return indexCounter.get();
	}
}