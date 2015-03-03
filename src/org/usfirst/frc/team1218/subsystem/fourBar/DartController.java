package org.usfirst.frc.team1218.subsystem.fourBar;


import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class DartController extends CANTalon implements PIDSource, PIDOutput{
	
	final double BOTTOM_SOFT_LIMIT = 0.3;
	final double TOP_SOFT_LIMIT = 0.7;
	
	final double MAX_AMPERAGE = 1;
	final double OVERAMPERAGE_COOLDOWN_TIME = 5;
	
	double lastOverampTime = -100; //too long ago to worry about
	
	private boolean enabled;
	
	protected final AnalogPotentiometer potentiometer;
	
	public DartController(int deviceNumber, int potentiometerPort) {
		super(deviceNumber);
		
		enabled = true;
		
		potentiometer = new AnalogPotentiometer(potentiometerPort);
		
		changeControlMode(ControlMode.PercentVbus);
    	enableLimitSwitch(true, true);
    	ConfigFwdLimitSwitchNormallyOpen(false);
    	ConfigRevLimitSwitchNormallyOpen(false);
    	enableBrakeMode(true);
	}
	
	public void disable() {
		enabled = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public double get() {
		return getPosition();
	}
	
	public double getPosition() {
		return potentiometer.get();
	}
	
	public double getPower () {
		return super.get();
	}
	
	public void set(double speed) {
		setPower(speed);
	}
	
	public double getCurrent() {
		return super.getOutputCurrent();
	}
	
	public void setPower(double power) {
		if (safetyCheck(power)) {
			super.set(power);
		}
		else{
			super.set(0);
		}
	}
	
	boolean safetyCheck(){
		return safetyCheck(super.get());
	}
	
	boolean safetyCheck(double power) {
		if (enabled) {
			if (getPosition() < BOTTOM_SOFT_LIMIT && power<= 0) { //if below bottom soft limit and going down
				super.set(0);
				System.out.println("bottom soft limit hit" + power);
				return false;
			}
			if (getPosition() > TOP_SOFT_LIMIT && power >= 0) { //if above top soft limit and going up
				super.set(0);
				System.out.println("soft top limit hit" + power);
				return false;
			}
			if (timeSinceAmpFault() < OVERAMPERAGE_COOLDOWN_TIME) {
				System.out.println("cooling off from overamp fault");
				super.set(0);
				return false;
			}
			if (super.getOutputCurrent() > MAX_AMPERAGE ) {
				super.set(0);
				lastOverampTime = Timer.getFPGATimestamp();
				System.out.println("diabled because over max amperage");
				return false;
			}
			else {
				return true;
			}
		}
		else {
			super.set(0);
			return false;
		}
		
		
	}	
	
	double timeSinceAmpFault() {
		return Timer.getFPGATimestamp() - lastOverampTime;
	}
	
	public double pidGet() {
		return getPosition();
	}
	
	public void pidWrite(double speed) {
		setPower(speed);
	}
	
}
