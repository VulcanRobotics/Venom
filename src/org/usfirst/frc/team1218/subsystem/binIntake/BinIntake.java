package org.usfirst.frc.team1218.subsystem.binIntake;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		initIntakeWheel(intakeL);
		initIntakeWheel(intakeR);
		clamp = new Solenoid(RobotMap.ESCALATOR_INTAKE_SOLENOID);
		binDetector = new DigitalInput(RobotMap.ESCALATOR_INTAKE_DETECTOR);
	}

    private static void initIntakeWheel(CANTalon intakeWheelController) {
		intakeWheelController.enableBrakeMode(false);
		intakeWheelController.setExpiration(1000);
		intakeWheelController.setSafetyEnabled(true);
		int pollRate = 1000;
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.AnalogTempVbat, pollRate);
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, pollRate);
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.General, pollRate);
		intakeWheelController.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, pollRate);
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
    
    public void syncDashboard() {
    	SmartDashboard.putBoolean("Escalator_Clamps_Open", clamp.get());
    	SmartDashboard.putNumber("Escalator_Intake_Power", intakeL.get());
    }
    
    public boolean getHasBin() {
    	//should return false if not plugged in
    	return binDetector.get();
    }
}

