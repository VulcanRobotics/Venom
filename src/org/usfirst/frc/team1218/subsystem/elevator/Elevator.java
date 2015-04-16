package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.commands.elevator.ManualControl;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
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
	
	//TODO remove this set of constants?
	public static final double P = 1.3;
	public static final double I = 0.0;
	public static final double D = 0.0;
	
	public static final double P_UP = 0.7;
	public static final double I_UP = 0;
	public static final double D_UP = 0;
	
	public static final double P_DOWN = 1.3;
	public static final double I_DOWN = 0;
	public static final double D_DOWN = 0;
	
	public static final double ELEVATOR_POSITIONING_POWER = 1.0;
	public static final double ELEVATOR_MIN_POSITIONING_POWER_UP = 0.15;
	public static final double ELEVATOR_MIN_POSITIONING_POWER_DOWN = 0.2;
		
	public static final int TOP_SOFT_LIMIT = 4350;
	public static final int BOTTOM_SOFT_LIMT = 0;
	public static final double SLOWDOWN_NEAR_LIMIT_DISTANCE = 1000;
	
	public static final double BOTTOM_CLEARANCE = 200;
	public static final double TOP_CLEARANCE = 200;
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualControl());
    }
    
    public Elevator() {
    	elevatorController = new CANTalon(RobotMap.ELEVATOR_CONTROLLER, 300);
    	elevatorController.enableBrakeMode(true);
    	elevatorController.setReverseSoftLimit(BOTTOM_SOFT_LIMT);
    	elevatorController.setForwardSoftLimit(TOP_SOFT_LIMIT);
    	enableSoftLimits(true);

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
    	if (position > elevatorController.getEncPosition()) {
    		elevatorController.setPID(P_UP, I_UP, D_UP);
    	} else {
    		elevatorController.setPID(P_DOWN, I_DOWN, D_DOWN);
    	}
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
    
    public boolean atBottom() {
    	return Robot.elevator.getBottomLimit() || Robot.elevator.getPosition() <= Elevator.BOTTOM_SOFT_LIMT + BOTTOM_CLEARANCE;
    }
    
    public boolean atTop() {
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
    	rumble();
    }
    
    public void rumble() {
    	if (DriverStation.getInstance().isEnabled() && !atTop()) {
    		if (elevatorController.getSpeed() == 0) {
        		OI.setRumble(OI.RumblePosition.CENTER, true); //enabled, not at top, speed = 0
        	} else if (elevatorController.getSpeed() > 0) { 
            	OI.setRumble(OI.RumblePosition.RIGHT, true);//enabled, not at top, speed > 0
            } else {
            	OI.setRumble(OI.RumblePosition.LEFT, true); //enabled not at top speed < 0
            }
        } else {
    		OI.setRumble(OI.RumblePosition.CENTER, false);//not enabled
    	}
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