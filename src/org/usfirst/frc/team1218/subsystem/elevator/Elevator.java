package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
/**
 *@author afiol-mahon
 */
public class Elevator extends Subsystem {
    
	private final DigitalInput toteDetector; //Triggers if tote is inside robot able to be manipulated by elevator
	
	private CANTalon liftMaster;
	private CANTalon liftSlave;
	
	private static final double ELEVATOR_P = 0.01;
	private static final double ELEVATOR_I = 0.0;
	private static final double ELEVATOR_D = 0.0;
	

	public static final int ELEVATOR_SOFT_LIMIT_BOTTOM = 200;
	public static final int ELEVATOR_DROP_POSITION = 250;
	public static final int ELEVATOR_CAPTURE_POSITION = 500; //Minimum height needed to get tote in brushes
	public static final int ELEVATOR_STEP_POSITION = 3000; // could we just lower elevator to ground to drop offf totes?
	public static final int ELEVATOR_CLEARNCE_POSITION = 4000; //height that stored totes clear totes on ground
	public static final int ELEVATOR_RAISE_POSITION = 5000;
	public static final int ELEVATOR_SOFT_LIMIT_TOP = 5500;

	public static final double ZERO_SPEED = -0.2;
	

	//TODO give better names
	
	
    public void initDefaultCommand() {}
    
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
    
    protected void configureElevatorMotorControllers() {
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
    	this.setElevator(ELEVATOR_RAISE_POSITION);
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
     * @param enabled true for Position PID control, false for manual control
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
    
    protected void zeroSensor() {
    	liftMaster.setPosition(0);
    }
    
    protected boolean getBottomLimit() {
    	//if 1, limit switch hit
    	return liftMaster.getFaultRevLim() == 1;
    }
}

