package org.usfirst.frc.team1218.subsystem.swerve.math;

/**
 * 
 * @author afiol-mahon
 *
 */
public class Test_Angle {
	/**
	 * Test get360Angle()
	 * @param args
	 */
	public static void main(String[] args) {
		int minRange = -1000;
		final int maxRange = 1000;
		for (int i = minRange; i < maxRange; i++) {
			System.out.println("Angle In: " + i + " Cleaned Angle: " + Angle.get360Angle(i));
		}
	}
}