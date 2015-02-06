package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *@author afiol-mahon
 */
public class Elevator extends Subsystem {
    
	private final DigitalInput toteDetector;
	
	private final CANTalon liftMaster;
	private final CANTalon liftSlave;
	
	private static final double ELEVATOR_P = 0.01;
	private static final double ELEVATOR_I = 0.0;
	private static final double ELEVATOR_D = 0.0;
	
	public static final int ELEVATOR_SOFT_LIMIT_BOTTOM = 200;
	public static final int ELEVATOR_DROP_POSITION = 250;
	public static final int ELEVATOR_CAPTURE_POSITION = 500; //Minimum height needed to get tote in brushes
	public static final int ELEVATOR_STEP_POSITION = 3000;
	public static final int ELEVATOR_CLEARNCE_POSITION = 4000;
	public static final int ELEVATOR_RAISE_POSITION = 5000;
	public static final int ELEVATOR_SOFT_LIMIT_TOP = 5500;

	public static final double ZERO_SPEED = -0.2;
	
	
    public void initDefaultCommand() {
        setDefaultCommand(new C_ElevatorDefault());
    }
    
    public Elevator() {
    	liftMaster = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER);
    	liftSlave = new CANTalon(RobotMap.ELEVATOR_LIFT_SLAVE);
    	configureElevatorMotorControllers();
    	toteDetector = new DigitalInput(RobotMap.TOTE_DETECTOR);
    }
   
    
    /**
     * Set position of elevator
     * @param setpoint value from 0-1023
     */
    public void setElevatorSetpoint(int setpoint) {
    	liftMaster.set(setpoint);
    }
    
    public void setElevatorSpeed(double velocity) {
    	liftMaster.set(velocity);
    }
    
    private void configureElevatorMotorControllers() {
    	liftMaster.enableBrakeMode(true);
    	liftSlave.enableBrakeMode(true);
    	
    	liftMaster.enableLimitSwitch(true, true);
    	liftMaster.ConfigFwdLimitSwitchNormallyOpen(false);
    	liftMaster.ConfigRevLimitSwitchNormallyOpen(false);
    	liftMaster.setForwardSoftLimit(ELEVATOR_SOFT_LIMIT_TOP);
    	liftMaster.enableForwardSoftLimit(true);
    	liftMaster.setReverseSoftLimit(ELEVATOR_SOFT_LIMIT_BOTTOM);
    	liftMaster.enableReverseSoftLimit(true);
    	
    	liftSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	liftSlave.reverseOutput(true);
    	liftSlave.set(liftMaster.getDeviceID());
	}
    
   int getPosition() {
	   //TODO: check this get position
	   return liftMaster.getEncPosition();
   }
    
    void configureElevatorMotorControllersForPID() {
    	liftMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	liftMaster.changeControlMode(CANTalon.ControlMode.Position);
    	liftMaster.setPID(ELEVATOR_P, ELEVATOR_I, ELEVATOR_D);
    }
    
    void configureElevatorMotorControllersForSpeedControl() {
    	//should work with broken encoder
    	liftMaster.changeControlMode(CANTalon.ControlMode.Speed);
    	liftMaster.enableLimitSwitch(true, true);
    	liftMaster.enableForwardSoftLimit(false);
    	liftMaster.enableReverseSoftLimit(false);
    }
    
    public boolean getHasTote() {
    	return toteDetector.get();
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("Elevator_Position", liftMaster.getSetpoint());
    	//SmartDashboard.putNumber("Intake_Power", intakeL.getSetpoint());
    }
}

