package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.commands.elevator.ElevatorDefaultCommand;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class Elevator extends Subsystem {
	
	private boolean softLimitsEnabled = false;
	
	private final CANTalon elevatorController;	
	private final DigitalInput toteDetector;
	private final DigitalOutput toteIndicator;
		
	
	public static final double P = 1.3;
	public static final double I = 0.0;
	public static final double D = 0.0;
	
	public static final double ELEVATOR_POSITIONING_POWER = 1.0;
	public static final double ELEVATOR_MIN_POSITIONING_POWER_UP = 0.3;
	public static final double ELEVATOR_MIN_POSITIONING_POWER_DOWN = 0.2;
		
	public static final int TOP_SOFT_LIMIT = 4150;
	public static final int BOTTOM_SOFT_LIMT = 0;
	public static final int ELEVATOR_STARTING_POSITION = 4150;
	public static final double SLOWDOWN_NEAR_LIMIT_DISTANCE = 1000;
	public static final double MIN_SPEED = 100; //clicks per .1 seconds
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ElevatorDefaultCommand());
    }
    
    public Elevator() {
    	elevatorController = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER, 300);
    	elevatorController.enableBrakeMode(true);
    	
    	elevatorController.setReverseSoftLimit(BOTTOM_SOFT_LIMT);
    	elevatorController.setForwardSoftLimit(TOP_SOFT_LIMIT);
    	enableSoftLimits(false);

    	elevatorController.enableLimitSwitch(true, true);
    	elevatorController.ConfigFwdLimitSwitchNormallyOpen(false);
    	elevatorController.ConfigRevLimitSwitchNormallyOpen(false);
    	elevatorController.changeControlMode(ControlMode.PercentVbus);
    	
    	toteDetector = new DigitalInput(RobotMap.ELEVATOR_TOTE_DETECTOR);
    	toteIndicator = new DigitalOutput(RobotMap.ELEVATOR_TOTE_INDICATOR);
    	
    	
    	elevatorController.setPID(P, I, D);
    	enablePID(true);
    }
    
    public void enablePID(boolean shouldEnable) {
    	if (shouldEnable) {
    		elevatorController.changeControlMode(ControlMode.Position);
    	}
    	else {
    		elevatorController.changeControlMode(ControlMode.PercentVbus);
    	}
    }
    
    public void setPosition(double position) {
    	enablePID(true);
    	elevatorController.set(position);
    }
    
    public double getPosition() {
	   return elevatorController.getPosition();
    }
    
    public double getDistanceToTopLimit() {
    	double distance = Elevator.TOP_SOFT_LIMIT - getPosition();
    	if (distance < 0){
    		distance = 0;
    	}
    	return Elevator.TOP_SOFT_LIMIT - getPosition();
    }
    
    public double getDistanceToBottomLimit() {
    	double distance = getPosition() - Elevator.BOTTOM_SOFT_LIMT;
    	if (distance < 0){
    		distance = 0;
    	}
    	return distance ;
    }
    
    public void checkSoftLimits() {
    	
    	if (softLimitsEnabled) {
    		double power =0;
    		if (getDistanceToTopLimit() < SLOWDOWN_NEAR_LIMIT_DISTANCE && power > 0) {
               	power = ELEVATOR_MIN_POSITIONING_POWER_UP;
               	setPower(power);
           	}
           	if (getDistanceToBottomLimit() < SLOWDOWN_NEAR_LIMIT_DISTANCE && power < 0) {
           		power = -ELEVATOR_MIN_POSITIONING_POWER_DOWN;
           		setPower(power);
           	}
    	}
    }
    
    public void setPower(double power) {
    	elevatorController.changeControlMode(ControlMode.PercentVbus);
    	if (softLimitsEnabled) {
    		if (getDistanceToTopLimit() < SLOWDOWN_NEAR_LIMIT_DISTANCE && power > 0) {
               	power = ELEVATOR_MIN_POSITIONING_POWER_UP;
           	}
           	if (getDistanceToBottomLimit() < SLOWDOWN_NEAR_LIMIT_DISTANCE && power < 0) {
           		power = -ELEVATOR_MIN_POSITIONING_POWER_DOWN;
           	}
           	/*
           	if (Math.abs(power) < ELEVATOR_MIN_POSITIONING_POWER) {
           		System.out.println("below minimum power");
           		double sign = Math.signum(power);
           		power = ELEVATOR_MIN_POSITIONING_POWER * sign;
           	}
           	/*
           	if (elevatorController.getSpeed() < MIN_SPEED){
           		power = power / elevatorController.getSpeed();
           	}*/
    	}
    	System.out.println("set elevator power: "+ power + "time: "+ Timer.getFPGATimestamp());
    	elevatorController.set(power);
    }
     
    public double getPower() {
    	return elevatorController.get();
    }
    
    public boolean getHasTote() {
    	return toteDetector.get();
    }
    
   public boolean getTopLimit() {
    	return !elevatorController.isFwdLimitSwitchClosed() || elevatorController.getFaultForSoftLim() == 1;//TODO check behavior of method getFaultForSoftLim()
    }
    
    public boolean getBottomLimit() {
    	return !elevatorController.isRevLimitSwitchClosed() || elevatorController.getFaultRevSoftLim() == 1;
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
    
    public void setEncoderPosition(double position) {
    	elevatorController.setPosition(position);
    }
    
    public boolean atEncoderReference() {
    	return !elevatorController.isFwdLimitSwitchClosed();
    }
    
    public void enableSoftLimits(boolean enabled) {
    	elevatorController.enableForwardSoftLimit(enabled);
    	elevatorController.enableReverseSoftLimit(enabled);
    	softLimitsEnabled = enabled;
    }
}