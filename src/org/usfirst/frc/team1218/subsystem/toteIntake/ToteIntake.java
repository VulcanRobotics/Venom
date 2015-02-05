package org.usfirst.frc.team1218.subsystem.toteIntake;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 *	@author Bob Marley
 */
public class ToteIntake extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	protected final double TOTE_INTAKE_POWER = 1.0;
	
    public void initDefaultCommand() {
        
    }
    
    /**
     * Set state for intake motors
     * @param power positive value for intake and negative value for output
     */
    public ToteIntake() {
    	intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
    	intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
    }
    
    public void setIntakePower(double power) {//TODO Verify direction is correct
    	intakeL.set(power);
    	intakeR.set(-power);
    }

	public void syncDashboard() {
		SmartDashboard.putNumber("Tote_Intake_Power", intakeL.get());
	}
    
}

