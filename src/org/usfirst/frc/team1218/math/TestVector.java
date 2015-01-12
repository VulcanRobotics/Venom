package org.usfirst.frc.team1218.math;

public class TestVector {
	public static void main(String[] args) {
		Vector vector = new Vector(0, 1);
		vector.setAngle(-180);
		vector.getAngle();
		System.out.println("Vector angle: " + (int) vector.getAngle()
				+ "Vector X: " + (int) vector.getX()
				+ "Vector Y: " + (int) vector.getY());
	}
}
