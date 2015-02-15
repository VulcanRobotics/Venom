package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class FourBar extends Subsystem {
	private final Solenoid clamp;
	
	private final CANTalon binIntakeL;
	private final CANTalon binIntakeR;
	
	protected final CANTalon dartL;
	protected final CANTalon dartR;
	
	protected final PIDController dartLController;
	protected final PIDController dartRController;
	
	protected final AnalogPotentiometer dartLPot;
	protected final AnalogPotentiometer dartRPot;
	
	private static final double DART_P = 1.0;
	private static final double DART_I = 0.00001;
	private static final double DART_D = 0.0;
	
	protected static final double DART_FAILSAFE_DISTANCE = 70;
	protected static final double DART_REALIGN_DISTANCE = 25;
	protected static final double DART_REALIGN_POWER = 0.2;
		
	public static final int FOUR_BAR_HIGH_POSITION = 800;
	public static final int FOUR_BAR_MIDDLE_POSITION = 500;
	public static final int FOUR_BAR_LOW_POSITION = 200;
	
	public FourBar() {
		dartL = new CANTalon(RobotMap.FOUR_BAR_LEFT_DART);
		dartLPot = new AnalogPotentiometer(RobotMap.LEFT_DART_POTENTIOMETER);
		dartLController = new PIDController(DART_P, DART_I, DART_D, dartLPot, dartL);
		initDart(dartL);
		
		dartR = new CANTalon(RobotMap.FOUR_BAR_RIGHT_DART);
		dartRPot = new AnalogPotentiometer(RobotMap.RIGHT_DART_POTENTIOMETER);
		dartRController = new PIDController(DART_P, DART_I, DART_D, dartRPot, dartR);
		initDart(dartR);
		
		binIntakeL = new CANTalon(RobotMap.BIN_INTAKE_L);
		binIntakeR = new CANTalon(RobotMap.BIN_INTAKE_R);
		
		clamp = new Solenoid(RobotMap.BIN_INTAKE_SOLENOID);
		System.out.println("Four Bar Initialized");
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new C_FourBarDefault());
    }
    
    /**
     * Open Bin Grabber
     * @param opened true for open grabber
     */
    public void openGrabber(boolean opened) {
    	clamp.set(opened);
    }
    
    /**
     * @param power Positive value for intake, negative value for output
     */
    public void setIntake(double power) {
    	binIntakeL.set(power);
    	binIntakeR.set(-power);
    }
    
    public void setDartPosition(double setpoint) {
    	if (!Robot.dartSafety.dartKilled()) {
    		dartEnablePID(true);
    		dartLController.setSetpoint(setpoint);
    		dartRController.setSetpoint(setpoint);
    	}
    }
    
    public void setDartPower(double power) {
    	dartEnablePID(false);
    	if (!Robot.dartSafety.dartKilled()) {
        	dartL.set(power);
        	dartR.set(power);
    	} else {
    		dartL.set(0.0);
    		dartR.set(0.0);
    	}
    }
    
    /**
     * Configures motor controller for use with dart linear actuator
     * @param dartController dart to be configured
     */
    private static void initDart(CANTalon dartController) {
    	dartController.enableLimitSwitch(true, true);
    	dartController.ConfigFwdLimitSwitchNormallyOpen(false);
    	dartController.ConfigRevLimitSwitchNormallyOpen(false);
    	dartController.enableBrakeMode(true);
    }
    
    protected void dartEnablePID(boolean enabled) {
    	if (enabled) {
    		if(!Robot.dartSafety.dartKilled()) {
            	dartLController.setSetpoint(dartL.get());
        		dartLController.enable();
        		
            	dartRController.setSetpoint(dartL.get());
            	dartRController.enable();
        	}
    	} else {
    		dartLController.disable();
    		dartRController.disable();
    		setDartPower(0.0);
    	}
    }
    
    /**
     * @return Difference between current dart positions
     */
    protected double getDartPositionDifference() {
    	return Math.abs(Robot.fourBar.dartLPot.get() - Robot.fourBar.dartRPot.get());
    }
    
    protected void disableDarts() {
    	dartEnablePID(false);
    	dartL.disableControl();
    	dartR.disableControl();
    	dartLController.disable();
    	dartRController.disable();
    }
    
    protected void enableDarts() {
    	if (!Robot.dartSafety.dartKilled()) {
    		dartL.enableControl();
        	dartR.enableControl();
    	} else {
    		disableDarts();
    	}
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("FourBar_Left_Dart_Setpoint", dartLController.getSetpoint());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Setpoint", dartRController.getSetpoint());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Power", dartL.get());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Power", dartR.get());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Position", dartLPot.get());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Position", dartRPot.get());
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_PID_Enabled", dartLController.isEnable());
    	SmartDashboard.putBoolean("FourBar_Right_Dart_PID_Enabled", dartRController.isEnable());
    	
    	SmartDashboard.putBoolean("FourBar_Clamps_Open", clamp.get());
    	
    	SmartDashboard.putNumber("FourBar_Intake_Power", binIntakeL.get());
    }
}

