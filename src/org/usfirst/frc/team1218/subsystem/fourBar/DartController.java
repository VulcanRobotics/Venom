package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * This class controls an individual dart on the fourbar
 * @author afiolmahon
 */
public class DartController implements PIDSource, PIDOutput{
	
	protected static final double TOP_SOFT_LIMIT = 0.91;
	protected static final double BOTTOM_SOFT_LIMIT = 0.045;
		
	private boolean enabled;
	
	public final CANTalon talon;
	private final AnalogPotentiometer potentiometer;
		
	public static double SLOWDOWN_DISTANCE = 0.2;
	
	public DartController(int deviceNumber, int potentiometerPort) {
		enabled = true;
		talon = new CANTalon(deviceNumber, 300);
		potentiometer = new AnalogPotentiometer(potentiometerPort);
		talon.changeControlMode(ControlMode.PercentVbus);
		talon.enableLimitSwitch(true, true);
		talon.ConfigFwdLimitSwitchNormallyOpen(false);
		talon.ConfigRevLimitSwitchNormallyOpen(false);
		talon.enableBrakeMode(true);
	}
	
	/**
	 * Enable the dart
	 */
	public void enable() {
		if (!Robot.fourBar.isAlignmentSafe()) {
			enabled = true;
		} else {
			System.out.println("Cannot enable dart: DartSafety triggered...");
		}
	}
	
	/**
	 * Disable the dart
	 */
	public void disable() {
		enabled = false;
		talon.set(0.0);
	}
	
	/**
	 * Set if the dart will observe its hard limit switches
	 * @param enabled
	 */
	public void enableHardLimits(boolean enabled) {
		talon.enableLimitSwitch(enabled, enabled);
	}
	
	/**
	 * @return true if the dart is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @return Current dart position
	 */
	public double getPosition() {
		return potentiometer.get();
	}
	
	/**
	 * @return power output to talon
	 */
	public double getPower () {
		return talon.get();
	}
	
	/**
	 * @return Current in amps drawn from the dart
	 */
	public double getCurrent() {
		return talon.getOutputCurrent();
	}
	
	/**
	 * Safely sets dart power to dart after performing fourbar safety checks
	 * @param power
	 */
	public void setPower(double power) {
		if (safetyCheck(power) && Robot.fourBar.isAlignmentSafe() ) {
			talon.set(power);
		} else {
			talon.set(0);
			if (!Robot.fourBar.isAlignmentSafe()) {
				System.out.println("darts not aligned");
			}
		}
	}
	
	/**
	 * @return true if bottom limit is hit
	 */
	public boolean getBottomSoftLimit() {
		return getPosition() < BOTTOM_SOFT_LIMIT;
	}
	
	/**
	 * 
	 * @return true if top limit is hit
	 */
	public boolean getTopSoftLimit() {
		return getPosition() > TOP_SOFT_LIMIT;
	}
	
	/**
	 * Checks the fourbar is safe to move in the desired direction
	 * @param power
	 * @return true if movement is okay
	 */
	public boolean safetyCheck(double power) {
		if (enabled) {
			if (getPosition() < BOTTOM_SOFT_LIMIT && power <= 0) { //if below bottom soft limit and going down
				talon.set(0);
				System.out.println("Dart Safety Check Failed: Bottom Soft Limit Hit and power " + power + " was written to dart.");
				return false;
			}
			
			if (getPosition() > TOP_SOFT_LIMIT && power >= 0) { //if above top soft limit and going up
				talon.set(0);
				System.out.println("Dart Safety Check Failed: Top Soft Limit Hit and power " + power + " was written to dart.");
				return false;
			}
			
			return true;
		
		} else {
			talon.set(0);
			System.out.println("Dart Safety Check Failed: Dart is disabled.");
			return false;
		}
	}
	
	public boolean isFwdLimitSwitchClosed() {
		return talon.isFwdLimitSwitchClosed();
	}
	
	public boolean isRevLimitSwitchClosed() {
		return talon.isRevLimitSwitchClosed();
	}
	
	/*
	 * Implements pidGet and pidWrite so that DartController can be used by PIDController directly
	 */
	
	@Override
	public double pidGet() {
		return getPosition();
	}
	
	@Override
	public void pidWrite(double speed) {
		setPower(speed);
	}
	
}
