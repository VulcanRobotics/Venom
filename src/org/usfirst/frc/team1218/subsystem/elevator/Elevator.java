package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.commands.elevator.ElevatorCooldown;
import org.usfirst.frc.team1218.commands.elevator.ElevatorDefaultCommand;
import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.fourBar.DartController;

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
	
	boolean liftHasReferenced = false;
	
	private final CANTalon elevatorController;	
	private final DigitalInput toteDetector;
	private final DigitalOutput toteIndicator;
	
	private final double ELEVATOR_OVERAMP_COOLDOWN_TIME = 1.25;
	private final double ELEVATOR_MAX_CURRENT = 100;
	
	public static final double ELEVATOR_MANUAL_POSITIONING_POWER = 1.0;
	
	public boolean shouldUseSoftLimits = true;//TODO reference this flag
	public static final int TOP_SOFT_LIMIT = 4350;
	public static final int BOTTOM_SOFT_LIMT = 0;
	public static final double SLOWDOWN_NEAR_LIMIT_DISTANCE = 200;
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
    }
    
    public double getPosition() {
	   return elevatorController.getPosition();
    }
    
    public boolean isCurrentSafe() {
    	return this.getCurrent() < ELEVATOR_MAX_CURRENT;
    }
    
    public double getDistanceToTopLimit() {
    	return Elevator.TOP_SOFT_LIMIT - getPosition();
    }
    
    public double getDistanceToBottomLimit() {
    	return getPosition() - Elevator.BOTTOM_SOFT_LIMT ;
    }
    
    public void setPower(double power) {
    	if (isCurrentSafe()) {
    		if (elevatorController.getSpeed() > MIN_SPEED && shouldUseSoftLimits){
    			if (getDistanceToTopLimit() < SLOWDOWN_NEAR_LIMIT_DISTANCE && power > 0) {
               		power *= getDistanceToTopLimit() / SLOWDOWN_NEAR_LIMIT_DISTANCE;
           		}
           		if (getDistanceToBottomLimit() < SLOWDOWN_NEAR_LIMIT_DISTANCE && power < 0) {
               		power *= getDistanceToBottomLimit() / SLOWDOWN_NEAR_LIMIT_DISTANCE;
               	}
    		}
    		elevatorController.set(power);
    	} else {
    		new ElevatorCooldown(ELEVATOR_OVERAMP_COOLDOWN_TIME).start();
    	}
    }
     
    public double getPower() {
    	return elevatorController.get();
    }
    
    public boolean getHasTote() {
    	return toteDetector.get();
    }
    
   public boolean getTopLimit() {
    	return !elevatorController.isFwdLimitSwitchClosed() || elevatorController.getFaultForSoftLim() == 1 ;//TODO check behavior of method getFaultForSoftLim()
    }
    
    public boolean getBottomLimit() {
    	return !elevatorController.isRevLimitSwitchClosed() || elevatorController.getFaultRevSoftLim() == 1 ;
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
    	elevatorController.setPosition(TOP_SOFT_LIMIT);
    }
    
    public boolean atReference() {
        return !elevatorController.isRevLimitSwitchClosed();
    }
    
    public void enableSoftLimits(boolean enabled) {
    	shouldUseSoftLimits = enabled;
    	elevatorController.enableForwardSoftLimit(enabled);
    	elevatorController.enableReverseSoftLimit(enabled);
    }
}