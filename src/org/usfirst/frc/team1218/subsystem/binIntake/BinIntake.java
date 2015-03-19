package org.usfirst.frc.team1218.subsystem.binIntake;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BinIntake extends Subsystem {
    
//private final Solenoid clamp;
	
	private final CANTalon binIntakeLeft;
	private final CANTalon binIntakeRight;
	
	public static final double INTAKE_POWER = 1.0;
	public static final double CONTINOUS_HOLD_POWER = 0.1;
	public static final double OUTPUT_POWER = -.2;
	
	public BinIntake() {
		binIntakeLeft = new CANTalon(RobotMap.BIN_INTAKE_L);
		binIntakeRight = new CANTalon(RobotMap.BIN_INTAKE_R);
		//clamp = new Solenoid(RobotMap.BIN_INTAKE_SOLENOID);
	}
	
    public void initDefaultCommand() {
    	
    }
    
    public void setClamp(boolean shouldOpen) {
    	//clamp.set(!shouldOpen);
    }
    
    public void setBinIntake(double power) {
    	binIntakeLeft.set(power);
    	binIntakeRight.set(-power);
    }
    
    public void runLeft(double power) {
    	binIntakeLeft.set(-power);
    	binIntakeRight.set(-power);
    }
    
    public void syncDashboard() {
    	//SmartDashboard.putBoolean("FourBar_Clamps_Open", clamp.get());
    	SmartDashboard.putNumber("FourBar_binIntakeLeft_Amperage", binIntakeLeft.getOutputCurrent());
    	SmartDashboard.putNumber("FourBar_binIntakeRight_Amperage", binIntakeRight.getOutputCurrent());
    	SmartDashboard.putNumber("FourBar_Intake_Power", binIntakeLeft.get());
    }
}

