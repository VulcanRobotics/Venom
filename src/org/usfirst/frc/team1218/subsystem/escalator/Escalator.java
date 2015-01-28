package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
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
		dartL.enableControl();
		dartR = new CANTalon(RobotMap.ESCALATOR_RIGHT_DART);
		initializeDart(dartR);
		dartR.enableControl();
		intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
		intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
		clamp = new Solenoid(RobotMap.ESCALATOR_CLAMP_SOLENOID);
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
     * @param setpoint value from 0-1023
     */
    public void setSetpoint(int setpoint) {
    	dartL.set(setpoint);
    	dartR.set(setpoint);
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
    	dartController.changeControlMode(CANTalon.ControlMode.Position);
    }    
}

