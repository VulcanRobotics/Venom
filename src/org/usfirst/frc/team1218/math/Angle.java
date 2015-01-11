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
	public static double diffBetweenAngles(double angle1, double angle2) {
		return (180 - Math.abs(Math.abs(Angle.get360Angle(angle1) - Angle.get360Angle(angle2)) - 180)); 
	}
}