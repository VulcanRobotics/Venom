package org.usfirst.frc.team1218.subsystem.fourBar;


import edu.wpi.first.wpilibj.CANTalon;

public class DartController extends CANTalon {
	
	public DartController(int deviceNumber) {
		super(deviceNumber);
		changeControlMode(ControlMode.PercentVbus);
    	enableLimitSwitch(true, true);
    	ConfigFwdLimitSwitchNormallyOpen(false);
    	ConfigRevLimitSwitchNormallyOpen(false);
    	enableBrakeMode(true);
	}
}
