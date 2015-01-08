package org.usfirst.frc.team1218.math;

public class MathUtils {
	/**
	 * Maps a range of values to another range of values
	 * @param input
	 * @param inmin
	 * @param inmax
	 * @param outmin
	 * @param outmax
	 * @return scaled input value
	 */
	public static double mapValues(double x, double inmin, double inmax, double outmin, double outmax) {
		return (x - inmin) * (outmax - outmin) / (inmax - inmin) + outmin;
	}
}
