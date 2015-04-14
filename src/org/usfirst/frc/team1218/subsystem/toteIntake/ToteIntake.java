package org.usfirst.frc.team1218.subsystem.toteIntake;

import org.usfirst.frc.team1218.robot.OI;
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
	
	private double powerL;
	private double powerR;
	
	public final static double TOTE_INTAKE_POWER = 0.8;
	public final static double TOTE_INTAKE_POWER_GENTLE = 0.4;
	public final static double TOTE_INTAKE_POWER_HOLD = 0.2;

	public void initDefaultCommand() {
        
    }
    
    public ToteIntake() {
    	intakeL = new CANTalon(RobotMap.TOTE_INTAKE_LEFT);
    	intakeR = new CANTalon(RobotMap.TOTE_INTAKE_RIGHT);
    }
    
    public void setPower(double power) {
    	powerL = power;
    	powerR = power;
    }

    public double getAverageCurrent() {
    	return (intakeL.getOutputCurrent() + intakeR.getOutputCurrent())/2;
    }
    
    public void intakeFromLeft(double power){
    	powerL = power;
    	update();
    }
    
    public void intakeFromRight(double power){
    	powerR = power;
    	update();
    }
    
    private void setLeft(double power) {
    	if (OI.getOperatorRotation() > 0){
    		intakeR.set(-power * (1 - OI.getOperatorRotation()));
    	}
    	else {
    		intakeR.set(-power);
    	}
    	
    }
    
    private void setRight(double power) {
    	if (OI.getOperatorRotation() < 0){
    		intakeL.set(power * (1 + OI.getOperatorRotation()));
    	} 
    	else {
    		intakeL.set(power);
    	}
    	
    }
    
    public void update(){
    	setRight(powerR);
    	setLeft(powerL);
    }
    
	public void syncDashboard() {
		update();
		SmartDashboard.putNumber("Tote_Intake_Power", intakeL.get());
		SmartDashboard.putNumber("Tote_Intake_Right_Power", intakeR.getOutputCurrent());
		SmartDashboard.putNumber("Tote_Intake_Left_Power", intakeL.getOutputCurrent());
	}
}

