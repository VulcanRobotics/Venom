package org.usfirst.frc.team1218.robot;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Utility Class that configures and optimizes the TalonSRX Controllers for specific tasks.
 * @author afiol-mahon
 *
 */
public class SRX_Profiler {
	/**
	 * Effectively Disables the controller status frame for a scenario where can feedback is unnecessary.
	 * Useful if tuning can bus utilization is necessary
	 * @param talon
	 */
	public static void optimizeForIntakeWheel(CANTalon talon) {
		talon.enableBrakeMode(false);
		talon.setExpiration(1000);
		talon.setSafetyEnabled(true);
		int pollRate = 5000;
		talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.AnalogTempVbat, pollRate);
		talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, pollRate);
		talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.General, pollRate);
		talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, pollRate);
	}
}
