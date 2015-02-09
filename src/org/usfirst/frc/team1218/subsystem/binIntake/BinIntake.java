package org.usfirst.frc.team1218.subsystem.binIntake;

import org.usfirst.frc.team1218.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 */
public class BinIntake extends Subsystem {
    
	private final DigitalInput binDetector;
	
	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	private final Solenoid clamp;
	
	
	public static final double INTAKE_SPEED = -0.75;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public BinIntake() {
		intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
		intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
		clamp = new Solenoid(RobotMap.ESCALATOR_INTAKE_SOLENOID);
		binDetector = new DigitalInput(RobotMap.ESCALATOR_INTAKE_DETECTOR);
	}
	
	public void openClamp(){
		setClamp(true);
	}
	
	public void closeClamp() {
		setClamp(false);
	}
	
	public void setClamp(boolean shouldOpen) {
		clamp.set(shouldOpen);
	}
	
	public void setSpeed(double speed) {
		//TODO: verify direction is correct
		intakeL.set(-1.0 * speed);
		intakeR.set(speed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean getHasBin() {
    	//should return false if not plugged in
    	return binDetector.get();
    }
}

