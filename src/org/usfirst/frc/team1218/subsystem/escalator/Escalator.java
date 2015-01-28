package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class Escalator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final Solenoid clamp;
	
	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	protected final CANTalon dartL;
	protected final CANTalon dartR;
	
	private static final double DART_P = 0.01;
	private static final double DART_I = 0.0;
	private static final double DART_D = 0.0;
	
	protected static final int ESCALATOR_HIGH_POSITION = 700;
	protected static final int ESCALATOR_MIDDLE_POSITION = 500;
	protected static final int ESCALATOR_LOW_POSITION = 300;
	
	public Escalator() {
		dartL = new CANTalon(RobotMap.ESCALATOR_LEFT_DART);
		initializeDart(dartL);
		dartR = new CANTalon(RobotMap.ESCALATOR_RIGHT_DART);
		initializeDart(dartR);
		intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
		intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
		clamp = new Solenoid(RobotMap.ESCALATOR_CLAMP_SOLENOID);
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
    	clamp.set(opened);
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
    private static void initializeDart(CANTalon dartController) {
    	dartController.enableLimitSwitch(true, true);
    	dartController.ConfigFwdLimitSwitchNormallyOpen(false);
    	dartController.ConfigRevLimitSwitchNormallyOpen(false);
    	dartController.enableBrakeMode(true);
    	dartController.setPID(DART_P, DART_I, DART_D);
    	dartController.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
    }
    
    protected void dartManualMode() {
    	dartL.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	dartR.changeControlMode(CANTalon.ControlMode.PercentVbus);
    }
    
    private void dartPositionMode() {
    	dartL.changeControlMode(CANTalon.ControlMode.Position);
    	dartL.set(dartL.get());
    	dartR.changeControlMode(CANTalon.ControlMode.Position);
    	dartR.set(dartR.get());
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("Escalator_Left_Dart_Setpoint", dartL.getSetpoint());
    	SmartDashboard.putNumber("Escalator_Right_Dart_Setpoint", dartR.getSetpoint());
    	SmartDashboard.putBoolean("Escalator_Clamps_Open", clamp.get());
    	SmartDashboard.putNumber("Escalator_Intake_Power", intakeL.get());
    }
}

