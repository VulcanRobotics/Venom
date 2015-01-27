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
	
	private final CANTalon dartL;
	private final CANTalon dartR;
	
	public Escalator() {
		dartL = new CANTalon(RobotMap.ESCALATOR_LEFT_DART);
		initializeDart(dartL);
		dartR = new CANTalon(RobotMap.ESCALATOR_RIGHT_DART);
		initializeDart(dartR);
		intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
		intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
		clamp = new Solenoid(RobotMap.ESCALATOR_CLAMP_SOLENOID);
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new C_EscalatorDefault());
    }
    
    /**
     * Close Bin Grabber
     * @param enabled true for closed
     */
    public void setGrabber(boolean enabled) {//TODO verify that true is closed clamps
    	clamp.set(enabled);
    }
    
    /**
     * @param power Positive value for intake, negative value for output
     */
    public void setIntake(double power) {//TODO Verify that positive value is intake and both motors move same direction
    	intakeL.set(power);
    	intakeR.set(-power);
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
    	dartController.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
    	dartController.changeControlMode(CANTalon.ControlMode.Position);
    }
}

