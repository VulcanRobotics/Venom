package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
    
	private final CANTalon liftL;
	private final CANTalon liftR;
	
	private final Encoder liftEncoder;
	
	private final DigitalInput liftUpperLimit;
	private final DigitalInput liftLowerLimit;
	
	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
    public void initDefaultCommand() {
        setDefaultCommand(new C_ElevatorDefault());
    }
    
    public Elevator() {
    	liftL = new CANTalon(RobotMap.ELEVATOR_LIFT_L);
    	liftR = new CANTalon(RobotMap.ELEVATOR_LIFT_R);
    	liftUpperLimit = new DigitalInput(RobotMap.ELEVATOR_LIFT_UPPER_LIMIT);
    	liftLowerLimit = new DigitalInput(RobotMap.ELEVATOR_LIFT_LOWER_LIMIT);
    	liftEncoder = new Encoder(
    			RobotMap.ELEVATOR_LIFT_ENCODER_A,
    			RobotMap.ELEVATOR_LIFT_ENCODER_B,
    			RobotMap.ELEVATOR_LIFT_ENCODER_I,
    			false);
    	intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
    	intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
    }
    
    /**
     * Lifts possessed tote and stacks another bin from below
     */
    public void lift() {//TODO write lift()
    	//Moves all bins up one
    	//Lift
    	//hooks out
    	//lift down
    }
    
    public void release() {//TODO write release()
    	//release hooks
    	//lower
    }
}

