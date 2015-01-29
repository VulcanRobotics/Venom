package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class Elevator extends Subsystem {
    
	private final CANTalon liftMaster;
	private final CANTalon liftSlave;
		
	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
	private static final double ELEVATOR_P = 0.01;
	private static final double ELEVATOR_I = 0.0;
	private static final double ELEVATOR_D = 0.0;
	
	public static final int ELEVATOR_DROP_POSITION = 200;
	public static final int ELEVATOR_RAISE_POSITION = 700;
	public static final int ELEVATOR_STEP_POSITION = 500;
	
	public static final double TOTE_INTAKE_POWER = 1.0;
	
    public void initDefaultCommand() {
        setDefaultCommand(new C_ElevatorDefault());
    }
    
    public Elevator() {
    	liftMaster = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER);
    	liftSlave = new CANTalon(RobotMap.ELEVATOR_LIFT_SLAVE);
    	configureElevatorMotorControllers(liftMaster, liftSlave);
    	intakeL = new CANTalon(RobotMap.ELEVATOR_INTAKE_L);
    	intakeR = new CANTalon(RobotMap.ELEVATOR_INTAKE_R);
    }

	/**
     * Set state for intake motors
     * @param power positive value for intake and negative value for output
     */
    public void setIntake(double power) {//TODO Verify direction is correct
    	intakeL.set(power);
    	intakeR.set(-power);
    }
    
    /**
     * Set position of elevator
     * @param setpoint value from 0-1023
     */
    public void setElevatorSetpoint(int setpoint) {
    	liftMaster.set(setpoint);
    }
    
    private void configureElevatorMotorControllers(CANTalon master, CANTalon slave) {
    	master.enableBrakeMode(true);
    	slave.enableBrakeMode(true);
    	master.enableLimitSwitch(true, true);
    	master.ConfigFwdLimitSwitchNormallyOpen(false);
    	master.ConfigRevLimitSwitchNormallyOpen(false);
    	master.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
    	master.changeControlMode(CANTalon.ControlMode.Position);
    	master.setPID(ELEVATOR_P, ELEVATOR_I, ELEVATOR_D);
    	slave.changeControlMode(CANTalon.ControlMode.Follower);
    	slave.reverseOutput(true);
    	slave.set(master.getDeviceID());
	}
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("Elevator_Position", liftMaster.getSetpoint());
    	SmartDashboard.putNumber("Intake_Power", intakeL.getSetpoint());
    }
}