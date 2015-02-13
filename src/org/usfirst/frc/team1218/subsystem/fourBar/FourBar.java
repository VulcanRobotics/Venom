package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
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
	
	private static final double DART_P = 4.0;
	private static final double DART_I = 0.0;
	private static final double DART_D = 0.0;
	
	protected static final double DART_FAILSAFE_DISTANCE = 70;
	protected static final double DART_REALIGN_DISTANCE = 25;
	protected static final double DART_REALIGN_POWER = 0.2;
	
	private static final int DART_SOFT_LIMIT_FORWARD = 1024;
	private static final int DART_SOFT_LIMIT_REVERSE = 0;
	
	public static final int FOUR_BAR_HIGH_POSITION = 600;
	public static final int FOUR_BAR_MIDDLE_POSITION = 500;
	public static final int FOUR_BAR_LOW_POSITION = 400;
	
	public FourBar() {
		dartL = new CANTalon(RobotMap.FOUR_BAR_LEFT_DART);
		initDart(dartL);
		dartR = new CANTalon(RobotMap.FOUR_BAR_RIGHT_DART);
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
    	dartL.changeControlMode(CANTalon.ControlMode.Position);
    	dartR.changeControlMode(CANTalon.ControlMode.Position);
    	dartL.set(setpoint);
    	dartR.set(setpoint);
    }
    
    public void setDartPower(double power) {
    	dartL.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	dartR.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	dartL.set(power);
    	dartR.set(power);
    }
    
    /**
     * Configures motor controller for use with dart linear actuator
     * @param dartController dart to be configured
     */
    private static void initDart(CANTalon dartController) {
    	dartController.enableLimitSwitch(true, true);
    	dartController.ConfigFwdLimitSwitchNormallyOpen(false);
    	dartController.ConfigRevLimitSwitchNormallyOpen(false);
    	dartController.setForwardSoftLimit(DART_SOFT_LIMIT_FORWARD);
    	dartController.setReverseSoftLimit(DART_SOFT_LIMIT_REVERSE);
    	dartController.enableBrakeMode(true);
    	dartController.setPID(DART_P, DART_I, DART_D);
    	dartController.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
    }
    
    protected void dartPositionMode() {
    	dartL.changeControlMode(CANTalon.ControlMode.Position);
    	dartL.set(dartL.get());
    	dartR.changeControlMode(CANTalon.ControlMode.Position);
    	dartR.set(dartL.get());
    }
    
    /**
     * @return Difference between current dart positions
     */
    protected double getDartPositionDifference() {
    	return Math.abs(Robot.fourBar.dartL.getAnalogInPosition() - Robot.fourBar.dartR.getAnalogInPosition());
    }
    
    protected void disableDarts() {
    	dartL.disableControl();
    	dartR.disableControl();
    }
    
    protected void enableDarts() {
    	dartL.enableControl();
    	dartR.enableControl();
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("FourBar_Left_Dart_Setpoint", dartL.getSetpoint());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Setpoint", dartR.getSetpoint());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Power", dartL.get());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Power", dartR.get());
    	
    	SmartDashboard.putNumber("FourBar_Left_Dart_Position", dartL.getPosition());
    	SmartDashboard.putNumber("FourBar_Right_Dart_Position", dartR.getPosition());
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_Manual_Control", (dartL.getControlMode() == CANTalon.ControlMode.PercentVbus));
    	SmartDashboard.putBoolean("FourBar_Right_Dart_Manual_Control", (dartR.getControlMode() == CANTalon.ControlMode.PercentVbus));
    	
    	SmartDashboard.putBoolean("FourBar_Left_Dart_Position_Control", (dartL.getControlMode() == CANTalon.ControlMode.Position));
    	SmartDashboard.putBoolean("FourBar_Right_Dart_Position_Control", (dartR.getControlMode() == CANTalon.ControlMode.Position));
    	
    	SmartDashboard.putBoolean("FourBar_Clamps_Open", clamp.get());
    	
    	SmartDashboard.putNumber("FourBar_Intake_Power", binIntakeL.get());
    }
}

