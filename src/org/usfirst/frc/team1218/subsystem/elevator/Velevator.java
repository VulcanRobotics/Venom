package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Velevator extends Subsystem {
    
	private final TalonSRX liftL;
	private final TalonSRX liftR;
	private final Encoder liftEncoder;
	private final DigitalInput liftUpperLimit;
	private final DigitalInput liftLowerLimit;
	
	private final TalonSRX intakeL;
	private final TalonSRX intakeR;
	
    public void initDefaultCommand() {
        setDefaultCommand(new C_ElevatorDefault());
    }
    
    public Velevator() {
    	liftL = new TalonSRX(RobotMap.ELEVATOR_LIFT_L);
    	liftR = new TalonSRX(RobotMap.ELEVATOR_LIFT_R);
    	liftUpperLimit = new DigitalInput(RobotMap.ELEVATOR_LIFT_UPPER_LIMIT);
    	liftLowerLimit = new DigitalInput(RobotMap.ELEVATOR_LIFT_LOWER_LIMIT);
    	liftEncoder = new Encoder(
    			RobotMap.ELEVATOR_LIFT_ENCODER_A,
    			RobotMap.ELEVATOR_LIFT_ENCODER_B,
    			RobotMap.ELEVATOR_LIFT_ENCODER_I,
    			false);
    	intakeL = new TalonSRX(RobotMap.ELEVATOR_INTAKE_L);
    	intakeR = new TalonSRX(RobotMap.ELEVATOR_INTAKE_R);
    }
    
    public void Ratchet() {
    	//TODO Moves all bins up one
    }
    
}

