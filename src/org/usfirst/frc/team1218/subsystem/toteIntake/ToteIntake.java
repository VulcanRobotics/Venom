package org.usfirst.frc.team1218.subsystem.toteIntake;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	@author Bob Marley
 */
public class ToteIntake extends Subsystem {

	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	public final static double TOTE_INTAKE_POWER = 0.8;
	public final static double TOTE_INTAKE_POWER_GENTLE = 0.4;
	public final static double TOTE_INTAKE_POWER_HOLD = 0.2;

	public void initDefaultCommand() {
        
    }
    
    /**
     * Set state for intake motors
     * @param power positive value for intake and negative value for output
     */
    public ToteIntake() {
    	intakeL = new CANTalon(RobotMap.TOTE_INTAKE_LEFT);
    	intakeR = new CANTalon(RobotMap.TOTE_INTAKE_RIGHT);
    }
    
    public void setPower(double power) {
    	intakeFromLeft(power);
    	intakeFromRight(power);
    }

    public double getAverageCurrent() {
    	return (intakeL.getOutputCurrent() + intakeR.getOutputCurrent())/2;
    }
    
    public void intakeFromLeft(double power) {
    	intakeR.set(-power);
    }
    
    public void intakeFromRight(double power) {
    	intakeL.set(power);
    }
    
	public void syncDashboard() {
		SmartDashboard.putNumber("Tote_Intake_Power", intakeL.get());
		SmartDashboard.putNumber("Tote_Intake_Right_Power", intakeL.getOutputCurrent());
		SmartDashboard.putNumber("Tote_Intake_Left_Power", intakeL.getOutputCurrent());
	}
}

