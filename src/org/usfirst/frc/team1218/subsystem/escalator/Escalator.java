package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class Escalator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//XXX private final Solenoid clamp;
	
	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	protected final CANTalon dartL;
	protected final CANTalon dartR;
	
	private static final double DART_P = 10.0;//TODO Test and Tune Dart PID under load
	private static final double DART_I = 0.0;
	private static final double DART_D = 0.0;
	
	private static final int DART_SOFT_LIMIT_FORWARD = 1024;//TODO Tune Dart soft limits
	private static final int DART_SOFT_LIMIT_REVERSE = 0;
	
	public static final int ESCALATOR_HIGH_POSITION = 700;//TODO Test and Tune Dart Preset Positions
	public static final int ESCALATOR_MIDDLE_POSITION = 500;
	public static final int ESCALATOR_LOW_POSITION = 200;
	
	public Escalator() {
		dartL = new CANTalon(RobotMap.ESCALATOR_LEFT_DART);
		initDart(dartL);
		dartR = new CANTalon(RobotMap.ESCALATOR_RIGHT_DART);
		initDart(dartR);
		intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
		initIntakeWheel(intakeL);
		intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
		initIntakeWheel(intakeR);
		//XXX clamp = new Solenoid(RobotMap.ESCALATOR_INTAKE_SOLENOID);
		System.out.println("Escalator Initialized");
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new C_EscalatorDefault());
    }
    
    /**
     * Open Bin Grabber
     * @param opened true for open grabber
     */
    public void openGrabber(boolean opened) {//TODO verify that true is open clamps
    	//XXX clamp.set(opened);
    }
    
    /**
     * @param power Positive value for intake, negative value for output
     */
    public void setIntake(double power) {//TODO Verify that positive value is intake and both motors move same direction
    	intakeL.set(power);
    	intakeR.set(-power);
    }
    
    /**
     * Set position of escalator
     * @param set value from 0-1023 if position, -1.0 to 1.0 if power.
     */
    public void setDarts(double set) {
    	dartL.set(set);
    	dartR.set(set);
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
    
    private static void initIntakeWheel(CANTalon intakeWheelController) {
		intakeWheelController.enableBrakeMode(false);
		intakeWheelController.setExpiration(1000);
		intakeWheelController.setSafetyEnabled(true);
		int pollRate = 5000;
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.AnalogTempVbat, pollRate);
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, pollRate);
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.General, pollRate);
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, pollRate);
	}
    
    protected void dartManualMode() {
    	dartL.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	dartR.changeControlMode(CANTalon.ControlMode.PercentVbus);
    }
    
    protected void dartPositionMode() {
    	dartL.changeControlMode(CANTalon.ControlMode.Position);
    	dartL.set(dartL.get());
    	dartR.changeControlMode(CANTalon.ControlMode.Position);
    	dartR.set(dartR.get());
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
    	SmartDashboard.putNumber("Escalator_Left_Dart_Setpoint", dartL.getSetpoint());
    	SmartDashboard.putNumber("Escalator_Right_Dart_Setpoint", dartR.getSetpoint());
    	SmartDashboard.putNumber("Escalator_Left_Dart_Power", dartL.get());
    	SmartDashboard.putNumber("Escalator_Right_Dart_Power", dartR.get());
    	SmartDashboard.putNumber("Escalator_Left_Dart_Position", dartL.getPosition());
    	SmartDashboard.putNumber("Escalator_Right_Dart_Position", dartR.getPosition());
    	SmartDashboard.putBoolean("Escalator_Left_Dart_Manual_Control", (dartL.getControlMode() == CANTalon.ControlMode.PercentVbus));
    	SmartDashboard.putBoolean("Escalator_Right_Dart_Manual_Control", (dartR.getControlMode() == CANTalon.ControlMode.PercentVbus));
    	SmartDashboard.putBoolean("Escalator_Left_Dart_Position_Control", (dartL.getControlMode() == CANTalon.ControlMode.Position));
    	SmartDashboard.putBoolean("Escalator_Right_Dart_Position_Control", (dartR.getControlMode() == CANTalon.ControlMode.Position));
    	//XXX SmartDashboard.putBoolean("Escalator_Clamps_Open", clamp.get());
    	SmartDashboard.putNumber("Escalator_Intake_Power", intakeL.get());
    }
}

