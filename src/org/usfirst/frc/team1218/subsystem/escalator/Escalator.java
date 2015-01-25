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
	Solenoid clamp;
	
	CANTalon intakeL;
	CANTalon intakeR;
	
	CANTalon dartL; //2 limit 1 pot
	CANTalon dartR;
	
	public Escalator() {
		dartL = new CANTalon(RobotMap.ESCALATOR_LEFT_DART);
		initializeDart(dartL);
		dartR = new CANTalon(RobotMap.ESCALATOR_RIGHT_DART);
		initializeDart(dartR);
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new C_EscalatorDefault());
    }
    
    /**
     * Configures motor controller for use with dart linear actuator
     * @param dartController
     */
    private static void initializeDart(CANTalon dartController) {
    	dartController.enableLimitSwitch(true, true);
    	dartController.ConfigFwdLimitSwitchNormallyOpen(false);
    	dartController.ConfigRevLimitSwitchNormallyOpen(false);
    	dartController.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
    }
}

