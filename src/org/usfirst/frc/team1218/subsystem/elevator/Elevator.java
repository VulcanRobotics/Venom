package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
    
	private final CANTalon liftMaster;
	private final CANTalon liftSlave;
		
	private final CANTalon intakeL;
	private final CANTalon intakeR;
	
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
    
    private void configureElevatorMotorControllers(CANTalon master, CANTalon slave) {
    	master.enableBrakeMode(true);
    	slave.enableBrakeMode(true);
    	master.enableLimitSwitch(true, true);
    	master.ConfigFwdLimitSwitchNormallyOpen(false);
    	master.ConfigRevLimitSwitchNormallyOpen(false);
    	master.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
    	master.changeControlMode(CANTalon.ControlMode.Position);
    	slave.changeControlMode(CANTalon.ControlMode.Follower);
    	slave.reverseOutput(true);
    	slave.set(master.getDeviceID());
	}
}