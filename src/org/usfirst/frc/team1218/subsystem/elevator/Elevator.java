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
	
	private final CANTalon liftMaster;
	private final CANTalon liftSlave;
	
	private static final double ELEVATOR_P = 0.01;
	private static final double ELEVATOR_I = 0.0;
	private static final double ELEVATOR_D = 0.0;
	
	public static final int ELEVATOR_DROP_POSITION = 200; 
	public static final int ELEVATOR_STEP_POSITION = 500;
	public static final int ELEVATOR_RAISE_POSITION = 700;
	public static final int ELEVATOR_DEFAULT_POSITION = ELEVATOR_RAISE_POSITION;
	
	public static final double ELEVATOR_POSITIONING_POWER = 1.0;
	public static final double ELEVATOR_REFERENCING_POWER = 0.5;
	
    public void initDefaultCommand() {}
    
    public Elevator() {
    	liftMaster = new CANTalon(RobotMap.ELEVATOR_LIFT_MASTER);
    	liftMaster.enableBrakeMode(true);
    	liftMaster.enableLimitSwitch(true, true);
    	liftMaster.ConfigFwdLimitSwitchNormallyOpen(false);
    	liftMaster.ConfigRevLimitSwitchNormallyOpen(false);
    	liftMaster.setPID(ELEVATOR_P, ELEVATOR_I, ELEVATOR_D);
    	liftMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	liftMaster.setSafetyEnabled(true);
    	liftMaster.setExpiration(0.1);
    	
    	liftSlave = new CANTalon(RobotMap.ELEVATOR_LIFT_SLAVE);
    	liftSlave.enableBrakeMode(true);
    	liftSlave.changeControlMode(CANTalon.ControlMode.Follower);
    	liftSlave.reverseOutput(true);
    	liftSlave.set(liftMaster.getDeviceID());
    }
    
    /**
     * @return Current position of tote elevator
     */
    public double getPosition() {
	   return liftMaster.getPosition();
    }
    
    
    public void setPosition(double position) {
    	liftMaster.changeControlMode(ControlMode.Position);
    	liftMaster.set(position);
    }
    
    public void setPower(double power) {
    	liftMaster.changeControlMode(ControlMode.PercentVbus);
    	liftMaster.set(power);
    }
        
    public void syncDashboard() {
    	SmartDashboard.putNumber("Elevator_Position", getPosition());
    }
    
    public boolean atReference() {
    	return liftMaster.isRevLimitSwitchClosed();
    }
    
    public void zeroPosition() {
    	liftMaster.setPosition(0);
    }
}