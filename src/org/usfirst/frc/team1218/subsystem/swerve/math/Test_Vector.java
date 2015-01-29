package org.usfirst.frc.team1218.subsystem.swerve.math;

/**
 * 
 * @author afiol-mahon
 *
 */
public class Test_Vector {
		
	public static void main(String[] args) {
		testScaleMagnitude(new Vector(1, 1), 0.5);
	}
	
	public static void testSetAngle(Vector vector, double angle) {
		System.out.println(vector.debug("Initial"));
		vector.setAngle(angle);
		System.out.println(vector.debug("With Angle " + String.valueOf(angle)));
	}
	
	public static void testSetMagnitude(Vector vector, double magnitude) {
		double angleI = vector.getAngle();
		System.out.println(vector.debug("Initial"));
		vector.setMagnitude(magnitude);
		System.out.println(vector.debug("With Magnitude " + String.valueOf(magnitude)));
		if (angleI == vector.getAngle()) {
			System.out.println("Angle Is Unchanged");
		} else {
			System.out.println("WARNING: Final Angle Differs from Initial Angle. AI[ " + angleI + " ] AF[ " + vector.getAngle() + " ]");
		}
	}
	
	public static void testScaleMagnitude(Vector vector, double scalar) {
		System.out.println(vector.debug("Initial"));
		vector.scaleMagnitude(scalar);
		System.out.println(vector.debug("Scaled by " + String.valueOf(scalar)));
	}
}
