package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.commands.elevator.ManualControl;
import org.usfirst.frc.team1218.robot.Robot;
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
		
	public static final int TOP_SOFT_LIMIT = 4350;
	public static final int BOTTOM_SOFT_LIMT = 0;
	public static final double SLOWDOWN_NEAR_LIMIT_DISTANCE = 1000;
	
	public static final double BOTTOM_CLEARANCE = 50;
	public static final double TOP_CLEARANCE = 100;
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualControl());
    }
    
    public Elevator() {
    	elevatorController = new CANTalon(RobotMap.ELEVATOR_CONTROLLER, 300);
    	elevatorController.enableBrakeMode(true);
    	elevatorController.setReverseSoftLimit(BOTTOM_SOFT_LIMT);
    	elevatorController.setForwardSoftLimit(TOP_SOFT_LIMIT);
    	enableSoftLimits(false);

    	elevatorController.enableLimitSwitch(true, false);
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
    	if (distance < 0) {
    		distance = 0;
    	}
    	return Elevator.TOP_SOFT_LIMIT - getPosition();
    }
    
    public double getDistanceToBottomLimit() {
    	double distance = getPosition() - Elevator.BOTTOM_SOFT_LIMT;
    	if (distance < 0) {
    		distance = 0;
    	}
    	return distance ;
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
    	}
    	elevatorController.set(power);
    }
     
    public double getPower() {
    	return elevatorController.get();
    }
    
    public boolean hasTote() {
    	return toteDetector.get();
    }
    
    public boolean atBottom(){
    	return Robot.elevator.getBottomLimit() || Robot.elevator.getPosition() <= Elevator.BOTTOM_SOFT_LIMT + BOTTOM_CLEARANCE;
    }
    
    public boolean atTop(){
    	return Robot.elevator.getTopLimit() || Robot.elevator.getPosition() > Elevator.TOP_SOFT_LIMIT-TOP_CLEARANCE;
    }
    
   public boolean getTopLimit() {
    	return !elevatorController.isFwdLimitSwitchClosed() || elevatorController.getFaultForSoftLim() == 1;
    }
    
    public boolean getBottomLimit() {
    	return !elevatorController.isRevLimitSwitchClosed() || elevatorController.getFaultRevSoftLim() == 1;
    }
    
    public double getCurrent() {
    	return elevatorController.getOutputCurrent();
    }
    
    public void periodicTasks() {
    	SmartDashboard.putNumber("Elevator_Max_Height", TOP_SOFT_LIMIT);
    	SmartDashboard.putNumber("Elevator_Position", getPosition());
    	SmartDashboard.putBoolean("Elevator_Top_Hard_Limit", elevatorController.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("Elevator_Bottom_Hard_Limit", elevatorController.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("Elevator_Top_Soft_Limit", elevatorController.getFaultForSoftLim() == 1);
    	SmartDashboard.putBoolean("Elevator_Bottom_Soft_Limit", elevatorController.getFaultRevSoftLim() == 1);
    	SmartDashboard.putNumber("Elevator_Position_Error", elevatorController.getClosedLoopError());
    	SmartDashboard.putBoolean("Elevator_Has_Tote", hasTote());
    	SmartDashboard.putNumber("Elevator_Current", elevatorController.getOutputCurrent());
    	toteIndicator.set(hasTote());
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