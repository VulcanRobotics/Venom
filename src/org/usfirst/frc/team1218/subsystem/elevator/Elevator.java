package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.commands.elevator.ElevatorDefaultCommand;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class Elevator extends Subsystem {
	
	private final CANTalon elevatorController;	
	private final DigitalInput toteDetector;
	private final DigitalOutput toteIndicator;
		
	public static final double ELEVATOR_MANUAL_POSITIONING_POWER = 1.0;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ElevatorDefaultCommand());
    }
    
    public Elevator() {
    	elevatorController = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER, 300);
    	elevatorController.enableBrakeMode(true);

    	elevatorController.enableLimitSwitch(true, true);
    	elevatorController.ConfigFwdLimitSwitchNormallyOpen(false);
    	elevatorController.ConfigRevLimitSwitchNormallyOpen(false);
    	elevatorController.changeControlMode(ControlMode.PercentVbus);
    	
    	toteDetector = new DigitalInput(RobotMap.ELEVATOR_TOTE_DETECTOR);
    	toteIndicator = new DigitalOutput(RobotMap.ELEVATOR_TOTE_INDICATOR);
    }
    
    public double getPosition() {
	   return elevatorController.getPosition();
    }
    
    public void setPower(double power) {
    	elevatorController.set(power);
    }
    
    public boolean getHasTote() {
    	return toteDetector.get();
    }
    
   public boolean getTopLimit() {
    	return !elevatorController.isFwdLimitSwitchClosed();
    }
    
    public boolean getBottomLimit() {
    	return !elevatorController.isRevLimitSwitchClosed();
    }
    
    public double getCurrent() {
    	return elevatorController.getOutputCurrent();
    }
    
    public void periodicTasks() {
    	SmartDashboard.putNumber("Elevator_Position", getPosition());
    	SmartDashboard.putBoolean("Elevator_Upper_Limit", elevatorController.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("Elevator_Lower_Limit", elevatorController.isRevLimitSwitchClosed());
    	SmartDashboard.putNumber("Elevator_Position_Error", elevatorController.getClosedLoopError());
    	SmartDashboard.putBoolean("Elevator_Has_Tote", getHasTote());
    	SmartDashboard.putNumber("Elevator_Current", elevatorController.getOutputCurrent());
    	toteIndicator.set(getHasTote());
    }
}