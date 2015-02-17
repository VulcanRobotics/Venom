package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class FourBar extends Subsystem {
	
	protected final CANTalon dartLeft;
	protected final CANTalon dartRight;
	
	protected final PIDController dartLeftPIDController;
	protected final PIDController dartRightPIDController;
	
	protected final AnalogPotentiometer dartLeftPotentiometer;
	protected final AnalogPotentiometer dartRightPotentiometer;
	
	private static final double DART_P = 1.0;
	private static final double DART_I = 0.00001;
	private static final double DART_D = 0.0;
	
	protected static final double DART_FAILSAFE_DISTANCE = 0.1;
	protected static final double DART_REALIGN_DISTANCE = 0.01;
	protected static final double DART_REALIGN_POWER = 0.2;
		
	public static final double FOUR_BAR_HIGH_POSITION = 0.7;
	public static final double FOUR_BAR_LOW_POSITION = 0.2;
	
	public FourBar() {
		dartLeft = new CANTalon(RobotMap.FOUR_BAR_LEFT_DART);
		dartLeftPotentiometer = new AnalogPotentiometer(RobotMap.LEFT_DART_POTENTIOMETER);
		dartLeftPIDController = new PIDController(DART_P, DART_I, DART_D, dartLeftPotentiometer, dartLeft);
		initDart(dartLeft);
		
		dartRight = new CANTalon(RobotMap.FOUR_BAR_RIGHT_DART);
		dartRightPotentiometer = new AnalogPotentiometer(RobotMap.RIGHT_DART_POTENTIOMETER);
		dartRightPIDController = new PIDController(DART_P, DART_I, DART_D, dartRightPotentiometer, dartRight);
		initDart(dartRight);
		
		
		System.out.println("Four Bar Initialized");
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new C_FourBarDefault());
    }
    
    /**
     * Open Bin Grabber
     * @param opened true for open grabber
     */
   
    
    /**
     * @param power Positive value for intake, negative value for output
     */
  
    
    public void setDartPosition(double setpoint) {
    	if (!Robot.dartSafety.dartKilled()) {
    		dartEnablePID(true);
    		dartLeftPIDController.setSetpoint(setpoint);
    		dartRightPIDController.setSetpoint(setpoint);
    	}
    }
    
    public void setDartPower(double power) {
    	dartEnablePID(false);
    	if (!Robot.dartSafety.dartKilled()) {
        	dartLeft.set(power);
        	dartRight.set(power);
    	} else {
    		dartLeft.set(0.0);
    		dartRight.set(0.0);
    	}
    }
    
    /**
     * Configures motor controller for use with dart linear actuator
     * @param dartController dart to be configured
     */
    private static void initDart(CANTalon dartController) {
    	dartController.changeControlMode(ControlMode.PercentVbus);
    	dartController.enableLimitSwitch(true, true);
    	dartController.ConfigFwdLimitSwitchNormallyOpen(false);
    	dartController.ConfigRevLimitSwitchNormallyOpen(false);
    	dartController.enableBrakeMode(true);
    }
    
    protected void dartEnablePID(boolean enabled) {
    	if (enabled) {
    		if(!Robot.dartSafety.dartKilled()) {
            	dartLeftPIDController.setSetpoint(dartLeft.get());
        		dartLeftPIDController.enable();
        		
            	dartRightPIDController.setSetpoint(dartLeft.get());
            	dartRightPIDController.enable();
        	}
    	} else {
    		dartLeftPIDController.disable();
    		dartRightPIDController.disable();
    	}
    }
    
    /**
     * @return Difference between current dart positions
     */
    protected double getDartPositionDifference() {
    	return Math.abs(Robot.fourBar.dartLeftPotentiometer.get() - Robot.fourBar.dartRightPotentiometer.get());
    }
    
    protected void disableDarts() {
    	dartEnablePID(false);
    	dartLeft.disableControl();
    	dartRight.disableControl();
    	dartLeftPIDController.disable();
    	dartRightPIDController.disable();
    }
    
    protected void enableDarts() {
    	if (!Robot.dartSafety.dartKilled()) {
    		dartLeft.enableControl();
        	dartRight.enableControl();
    	} else {
    		disableDarts();
    	}
    }
    
    public void syncDashboard() {
    	SmartDashboard.putBoolean("FourBar_isDartKilled", Robot.dartSafety.dartKilled());
    	SmartDashboard.putNumber("FourBar_Left_Dart_Setpoint", dartLeftPIDController.getSetpoint());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Setpoint", dartRightPIDController.getSetpoint());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Power", dartLeft.get());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Power", dartRight.get());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Position", dartLeftPotentiometer.get());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Position", dartRightPotentiometer.get());
    	
    	SmartDashboard.putNumber("FourBar_Dart_Position_Difference", getDartPositionDifference());
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_PID_Enabled", dartLeftPIDController.isEnable());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_PID_Enabled", dartRightPIDController.isEnable());
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_Fwd_Limit", dartLeft.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_Fwd_Limit", dartRight.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("FourBar_Left_Dart_Rev_Limit", dartLeft.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_Rev_Limit", dartRight.isRevLimitSwitchClosed());
    	
    	
    }
}

