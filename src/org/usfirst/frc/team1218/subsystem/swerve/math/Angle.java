package org.usfirst.frc.team1218.subsystem.swerve.math;

/**
 * @author afiolmahon
 */
public class Angle {
	
	/**
	 * @return angle from 0 to 360
	 */
	public static double get360Angle(double angle) {
		if ((angle < 0.0) || (angle > 360.0)) {
			if (angle < 0.0) angle = 360.0 - Math.abs(angle % 360); //forces angle to be a positive value.
			angle = angle % 360.0; //forces angle to be less than 360
		}
		return angle;
	}
	
	public static double diffBetweenAngles(double angle1, double angle2) {
		return (180 - Math.abs(Math.abs(Angle.get360Angle(angle1) - Angle.get360Angle(angle2)) - 180)); 
	}
}