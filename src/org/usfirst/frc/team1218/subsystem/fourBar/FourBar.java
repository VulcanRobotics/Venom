package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class FourBar extends Subsystem implements PIDSource{
	
	protected final DartController dartLeft;
	protected final DartController dartRight;

	protected final PIDController dartMasterPositionController;
	protected final PIDController dartSlavePositionController;
	
	
	private static final double DART_MASTER_P = 1.0;
	private static final double DART_MASTER_I = 0.0001;
	private static final double DART_MASTER_D = 0.0;
	
	private static final double DART_SLAVE_P = -40.0;
	private static final double DART_SLAVE_I = 0.0;
	private static final double DART_SLAVE_D = 0.0;
	
	protected static final double DART_ON_TARGET_MASTER_DISTANCE = 0.06;
	protected static final double DART_ON_TARGET_SLAVE_DISTANCE = 0.01;
	protected static final double DART_FAILSAFE_DISTANCE = 0.1;
	protected static final double DART_REALIGN_DISTANCE = 0.05;
	
	protected static final double DART_MAX_OUTPUT_POWER = 0.2;
	protected static final double DART_REALIGN_POWER = 0.2;
		
	
	public static final double FOUR_BAR_HIGH_POSITION = 0.7;
	public static final double FOUR_BAR_LOW_POSITION = 0.2;
	public static final double FOUR_BAR_GET_BIN_FROM_STEP_POSITION = 0.35; //TODO: find this
	public static final double FOUR_BAR_GET_NOODLE_POSITION = .42; //TODO: find this
	
	
	public FourBar() {
		dartLeft = new DartController(RobotMap.FOUR_BAR_LEFT_DART, RobotMap.LEFT_DART_POTENTIOMETER);
		dartMasterPositionController = new PIDController(DART_MASTER_P, DART_MASTER_I, DART_MASTER_D, dartLeft, dartLeft);
		dartMasterPositionController.setAbsoluteTolerance(DART_ON_TARGET_MASTER_DISTANCE);
		dartMasterPositionController.setOutputRange(-0.5, 0.5);
		
		dartRight = new DartController(RobotMap.FOUR_BAR_RIGHT_DART, RobotMap.RIGHT_DART_POTENTIOMETER);
		dartSlavePositionController = new PIDController(DART_SLAVE_P, DART_SLAVE_I, DART_SLAVE_D, dartRight, dartRight);
		dartSlavePositionController.setOutputRange(-DART_MAX_OUTPUT_POWER, DART_MAX_OUTPUT_POWER);
		dartSlavePositionController.enable();
		dartSlavePositionController.setAbsoluteTolerance(DART_ON_TARGET_SLAVE_DISTANCE);
		dartSlavePositionController.setSetpoint(0.0);
		
		System.out.println("Four Bar Initialized");
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new C_FourBarDefault());
    }
    
    public boolean isOnTarget() {
    	return dartMasterPositionController.onTarget();
    }
    
    public void setDartPosition(double setpoint) {
    	if (!Robot.dartSafety.dartKilled()) {
    		dartEnablePID(true);
    		dartMasterPositionController.setSetpoint(setpoint);
    	}
    }
    
    public void setDartPower(double power) {
    	dartEnablePID(false);
    	if (!Robot.dartSafety.dartKilled()) {
        	dartLeft.setPower(power * DART_MAX_OUTPUT_POWER);
    	} else {
    		dartLeft.set(0.0);
    	}
    }
    
    protected void dartEnablePID(boolean enabled) {
    	if (enabled) {
    		if(!Robot.dartSafety.dartKilled()) {
            	dartMasterPositionController.setSetpoint(dartLeft.get());
        		dartMasterPositionController.enable();
        	}
    	} else {
    		dartMasterPositionController.disable();
    	}
    }
    
    /**
     * @return Difference between current dart positions
     */
    protected double getDartPositionDifference() {
    	return Math.abs(Robot.fourBar.dartLeft.get() - Robot.fourBar.dartRight.get());
    }
    
    protected void disableDarts() {
    	dartEnablePID(false);
    	dartLeft.disableControl();
    	dartRight.disableControl();
    	dartMasterPositionController.disable();
    	dartSlavePositionController.disable();
    }
    
    protected void enableDarts() {
    	if (!Robot.dartSafety.dartKilled()) {
    		dartLeft.enableControl();
    		
        	dartRight.enableControl();
        	dartSlavePositionController.enable();
        	dartSlavePositionController.setSetpoint(0.0);
    	} else {
    		disableDarts();
    	}
    }
    
    public void syncDashboard() {
    	SmartDashboard.putBoolean("FourBar_isDartKilled", Robot.dartSafety.dartKilled());
    	SmartDashboard.putNumber("FourBar_Left_Dart_Setpoint", dartMasterPositionController.getSetpoint());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Setpoint", dartSlavePositionController.getSetpoint());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Power", dartLeft.getPower());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Power", dartRight.getPower());
    	
    	SmartDashboard.putNumber("FourBar_Right_Dart_Position", dartRight.getPosition());
    	SmartDashboard.putNumber("FourBar_Left_Dart_Position", dartLeft.getPosition());
    	
    	SmartDashboard.putNumber("FourBar_Right_Current", dartRight.getCurrent());
    	SmartDashboard.putNumber("FourBar_Left_Current", dartLeft.getCurrent());
    	
    	SmartDashboard.putNumber("FourBar_Dart_Position_Difference", getDartPositionDifference());
    	
    	
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_PID_Enabled", dartMasterPositionController.isEnable());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_PID_Enabled", dartSlavePositionController.isEnable());
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_Fwd_Limit", dartLeft.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_Fwd_Limit", dartRight.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("FourBar_Left_Dart_Rev_Limit", dartLeft.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_Rev_Limit", dartRight.isRevLimitSwitchClosed());
    	
    }

    /**
     * Returns distance between right and left dart
     */
	@Override
	public double pidGet() {
		return dartLeft.get() - dartRight.get();
	}
}

