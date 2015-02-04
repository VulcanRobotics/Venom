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
	
	public static final int ELEVATOR_DROP_POSITION = 200;
	public static final int ELEVATOR_RAISE_POSITION = 700;
	public static final int ELEVATOR_STEP_POSITION = 500;
	
	protected static final double TOTE_INTAKE_POWER = 1.0;
	
    public void initDefaultCommand() {
        setDefaultCommand(new C_ElevatorDefault());
    }
    
    public Elevator() {
    	liftMaster = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER);
    	liftSlave = new CANTalon(RobotMap.ELEVATOR_LIFT_SLAVE);
    	configureElevatorMotorControllers(liftMaster, liftSlave);
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
    
    private void configureElevatorMotorControllers(CANTalon master, CANTalon slave) {
    	master.enableBrakeMode(true);
    	slave.enableBrakeMode(true);
    	master.enableLimitSwitch(true, true);
    	master.ConfigFwdLimitSwitchNormallyOpen(false);
    	master.ConfigRevLimitSwitchNormallyOpen(false);
    	slave.changeControlMode(CANTalon.ControlMode.Follower);
    	slave.reverseOutput(true);
    	slave.set(master.getDeviceID());
	}
    
    private void configureElevatorMotorControllersForPID() {
    	liftMaster.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
    	liftMaster.changeControlMode(CANTalon.ControlMode.Position);
    	liftMaster.setPID(ELEVATOR_P, ELEVATOR_I, ELEVATOR_D);
    }
    
    void configureElevatorMotorControllersForSpeedControl() {
    	liftMaster.changeControlMode(CANTalon.ControlMode.Speed);
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("Elevator_Position", liftMaster.getSetpoint());
    	//SmartDashboard.putNumber("Intake_Power", intakeL.getSetpoint());
    }
}