package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 *	@author Bob Marley
 */
public class TotePickup extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	protected final double TOTE_INTAKE_POWER = 1.0;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Set state for intake motors
     * @param power positive value for intake and negative value for output
     */
    public TotePickup() {
    	intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
    	intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
    }
    
    public void setIntakePower(double power) {//TODO Verify direction is correct
    	intakeL.set(power);
    	intakeR.set(-power);
    }
    
}

