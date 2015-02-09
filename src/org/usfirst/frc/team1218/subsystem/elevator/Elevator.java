package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class Elevator extends Subsystem {
    
	//private final DigitalInput toteDetector; //Triggers if tote is inside robot able to be manipulated by elevator
	
	private final CANTalon liftMaster;
	private final CANTalon liftSlave;
	
	private static final double ELEVATOR_P = 0.01;
	private static final double ELEVATOR_I = 0.0;
	private static final double ELEVATOR_D = 0.0;
	
	//TODO give better names
	public static final int ELEVATOR_DROP_POSITION = 200; 
	public static final int ELEVATOR_CAPTURE_POSITION = 250; //Minimum height needed to get tote in brushes
	public static final int ELEVATOR_CLEARNCE_POSITION = 650;
	public static final int ELEVATOR_RAISE_POSITION = 700;
	public static final int ELEVATOR_STEP_POSITION = 500;
	public static final int ELEVATOR_DEFAULT_POSITION = 500;
	
    public void initDefaultCommand() {}
    
    public Elevator() {
    	liftMaster = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER);
    	liftMaster.enableBrakeMode(true);
    	liftMaster.enableLimitSwitch(true, true);
    	liftMaster.ConfigFwdLimitSwitchNormallyOpen(false);
    	liftMaster.ConfigRevLimitSwitchNormallyOpen(false);
    	liftMaster.setPID(ELEVATOR_P, ELEVATOR_I, ELEVATOR_D);
    	liftMaster.setFeedbackDevice(FeedbackDevice.AnalogPot);
    	
    	liftSlave = new CANTalon(RobotMap.ELEVATOR_LIFT_SLAVE);
    	liftSlave.enableBrakeMode(true);
    	liftSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	liftSlave.reverseOutput(true);
    	liftSlave.set(liftMaster.getDeviceID());
       	this.enablePID(true);
    	this.setElevator(ELEVATOR_DEFAULT_POSITION);
    }
    
    /**
     * Set position of elevator
     * @param setpoint value from 0-1023 or -1 to 1
     */
    public void setElevator(double setpoint) {
    	liftMaster.set(setpoint);
    }
    
    /**
     * @return Current position of tote elevator
     */
    public double getPosition() {
	   return liftMaster.getPosition();
    }
    
    
    /**
     * enable or disable the PIDController
     * @param enabled true for Position PID controll, false for manual control
     */
   	public void enablePID(boolean enabled) {
   		if (enabled) {
   			liftMaster.changeControlMode(ControlMode.Position);
   		} else {
   			liftMaster.changeControlMode(ControlMode.PercentVbus);
   		}
   	}
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("Elevator_Position", getPosition());
    }
}