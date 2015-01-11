package org.usfirst.frc.team1218.math;

public class Angle {
	
	/**
	 * 
	 * @return angle from 0 to 360
	 */
	public static double get360Angle(double angle) {
		double cleanAngle = angle;
		if (cleanAngle < 0.0) cleanAngle = 360.0 - Math.abs(cleanAngle); //forces angle to be a positive value.
		cleanAngle = cleanAngle % 360.0; //forces angle to be less than 360
		return cleanAngle;
	}
	
	/**
	 * 
	 * @param angleA
	 * @param angleB
	 * @return difference between angles in degrees
	 */
	public static double angleDifference(double angleA, double angleB) {
		return get360Angle(Math.abs(angleA - angleB) % 360.0);
	}
}
