package org.usfirst.frc.team1218.subsystem.fourBar;


import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class DartController implements PIDSource, PIDOutput{
	
	private static final double TOP_SOFT_LIMIT = 0.89;
	private static final double BOTTOM_SOFT_LIMIT = 0.05;
	
	private static final double MAX_AMPERAGE = 20.0;
	
	private boolean enabled;
	
	private final CANTalon talon;
	private final AnalogPotentiometer potentiometer;
	
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
	
	public void enable() {
		if (!Robot.fourBar.isAlignmentSafe()) {
			enabled = true;
		} else {
			System.out.println("Cannot enable dart: DartSafety has been triggered...");
		}
	}
	
	public void disable() {
		enabled = false;
		talon.set(0.0);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public double getPosition() {
		return potentiometer.get();
	}
	
	public double getPower () {
		return talon.get();
	}
	
	public double getCurrent() {
		return talon.getOutputCurrent();
	}
	
	public boolean isOverCurrent() {
		return getCurrent() > MAX_AMPERAGE;
	}
	
	public void setPower(double power) {
		if (safetyCheck(power) && !Robot.fourBar.isAlignmentSafe()) {
			talon.set(power);
		} else {
			talon.set(0);
		}
	}
	
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
			
			if (!Robot.fourBar.isCurrentSafe()) {
				talon.set(0);
				System.out.println("Dart Safety Check Failed: Maximum Amperage Exceeded.");
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
	
	public double pidGet() {
		return getPosition();
	}
	
	public void pidWrite(double speed) {
		setPower(speed);
	}
	
}
