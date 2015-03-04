package org.usfirst.frc.team1218.subsystem.elevator;

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
	
	boolean liftHasReferenced = false;
	
	private final CANTalon elevatorController;	
	private final DigitalInput toteDetector;
	private final DigitalOutput toteIndicator;
	
	private final double ELEVATOR_OVERAMP_COOLDOWN_TIME = 1.25;
	private final double ELEVATOR_MAX_CURRENT = 100;
	
	public static final double ELEVATOR_MANUAL_POSITIONING_POWER = 1.0;
	
	public boolean shouldUseSoftLimits = true;
	public static final int TOP_SOFT_LIMIT = 4350;
	public static final int BOTTOM_SOFT_LIMT = 0;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_ElevatorDefault());
    }
    
    public Elevator() {
    	elevatorController = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER, 300);
    	elevatorController.enableBrakeMode(true);
    	
    	elevatorController.setReverseSoftLimit(BOTTOM_SOFT_LIMT);
    	elevatorController.setForwardSoftLimit(TOP_SOFT_LIMIT);
    	disableSoftLimits();

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
    
    public void setPower(double power) {
    	if (isCurrentSafe()) {
    		elevatorController.set(power);
    	} else {
    		new C_ElevatorCooldown(ELEVATOR_OVERAMP_COOLDOWN_TIME).start();
    	}
    }
     
    public double getPower() {
    	return elevatorController.get();
    }
    
    public boolean getHasTote() {
    	return toteDetector.get();
    }
    
   protected boolean getTopLimit() {
    	return !elevatorController.isFwdLimitSwitchClosed() || elevatorController.getFaultForSoftLim() == 1 ;
    }
    
    protected boolean getBottomLimit() {
    	return !elevatorController.isRevLimitSwitchClosed() || elevatorController.getFaultRevSoftLim() == 1 ;
    }
    
    public double getCurrent() {
    	return elevatorController.getOutputCurrent();
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("Elevator_Position", getPosition());
    	SmartDashboard.putBoolean("Elevator_Upper_Limit", elevatorController.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("Elevator_Lower_Limit", elevatorController.isRevLimitSwitchClosed());
    	SmartDashboard.putNumber("Elevator_Position_Error", elevatorController.getClosedLoopError());
    	SmartDashboard.putBoolean("Elevator_Has_Tote", getHasTote());
    	SmartDashboard.putNumber("Elevator_Current", elevatorController.getOutputCurrent());
    	
    	if (!liftHasReferenced) {
    		checkForZero();
    	}
    	
    	toteIndicator.set(getHasTote());
    }
    
    
    public void checkForZero() {
        if (getBottomLimit()) {
            zeroPosition();
        }
    }
    
    public boolean atReference() {
        return !elevatorController.isRevLimitSwitchClosed();
    }
    
    void enableSoftLimits(boolean enabled) {
    	elevatorController.enableForwardSoftLimit(enabled);
    	elevatorController.enableReverseSoftLimit(enabled);
    }
    
    void enableSoftLimits() {
    	enableSoftLimits(true);
    }
    
    void disableSoftLimits() {
    	enableSoftLimits(false);
    }
    
    public void zeroPosition() {
    	elevatorController.setPosition(0);
        liftHasReferenced = true;
        enableSoftLimits();

    }
    }