package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.math.Angle;

import edu.wpi.first.wpilibj.Encoder;

public class AngleEncoder extends Encoder {
	private static final double ENCODER_CLICK_DEGREE_RATIO = 360.0 / 500; //Degrees over Number of Clicks
	
	public AngleEncoder(int aChannel, int bChannel, int iChannel, boolean reverseDirection) {
		super(aChannel, bChannel, iChannel, reverseDirection);
		this.setDistancePerPulse(ENCODER_CLICK_DEGREE_RATIO);
	}
	
	@Override
	public double pidGet() {
		return Angle.get360Angle(get());
	}
}