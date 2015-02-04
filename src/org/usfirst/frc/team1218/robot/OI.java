package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.subsystem.elevator.C_SetElevatorPosition;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.escalator.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.escalator.Escalator;
import org.usfirst.frc.team1218.subsystem.hooks.C_DeployHooks;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.swerve.C_GoToHeading;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team1218.subsystem.elevator.C_ElevatorManual;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * @author afiolmahon
 * @author liamcook
 */
public class OI {
	
    //Driver
	public static Joystick driver;
	public static Button resetGyro;
	public static Button maintainHeading;
	
	//Operator
	public static Joystick operator;
	
	public static Button lowerHooks;
	public static Button raiseHooks;
	
	public static Button elevatorRunToteIntake;
	public static Button elevatorDropStack;
	public static Button elevatorRaiseStack;
	public static Button elevatorToStepPosition;
	public static Button elevatorLower;
	public static Button elevatorRaise;
	
	public static Button escalatorRunBinIntake;
	public static Button escalatorOpenGrabber;
	public static Button escalatorHighPosition;
	public static Button escalatorMiddlePosition;
	public static Button escalatorLowPosition;
	
	//Control Panel
	public static Joystick controlPanel;
    
	public static Button manualControl;
	
	public OI() {
    	//Driver
    	driver = new Joystick(RobotMap.DRIVER_JOYSTICK);
    	
        resetGyro = new JoystickButton(driver, RobotMap.BUTTON_RESET_GYRO);
        resetGyro.whenPressed(new C_ZeroRobotHeading());
        
        maintainHeading = new JoystickButton(driver, RobotMap.BUTTON_MAINTAIN_HEADING);
        maintainHeading.whileHeld(new C_GoToHeading());
        
        //Operator
        operator = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        
        //Hooks
        lowerHooks = new JoystickButton(operator, RobotMap.BUTTON_LOWER_HOOKS);
        lowerHooks.whenPressed(new C_DeployHooks(true));
        
        raiseHooks = new JoystickButton(operator, RobotMap.BUTTON_RAISE_HOOKS);
        raiseHooks.whenPressed(new C_DeployHooks(false));
        	
        //Elevator
        elevatorRunToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RUN_TOTE_INTAKE);
        
        elevatorDropStack = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_DROP_STACK);
        elevatorDropStack.whenPressed(new C_SetElevatorPosition(Elevator.ELEVATOR_DROP_POSITION));
        	
        elevatorRaiseStack = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RAISE_STACK);
        elevatorRaiseStack.whenPressed(new C_SetElevatorPosition(Elevator.ELEVATOR_RAISE_POSITION));
        	
        elevatorToStepPosition = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_STEP_POSITION);
        elevatorToStepPosition.whenPressed(new C_SetElevatorPosition(Elevator.ELEVATOR_STEP_POSITION));
        
        elevatorRaise = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_UP);
        elevatorLower = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_DOWN);
        
        //Escalator
        escalatorRunBinIntake = new JoystickButton(operator, RobotMap.BUTTON_ESCALATOR_RUN_BIN_INTAKE);
        
        escalatorOpenGrabber = new JoystickButton(operator, RobotMap.BUTTON_ESCALATOR_OPEN_GRABBER);
        
        escalatorHighPosition = new JoystickButton(operator, RobotMap.BUTTON_ESCALATOR_HIGH_POSITION);
        escalatorHighPosition.whileHeld(new C_SeekPosition(Escalator.ESCALATOR_HIGH_POSITION));
        	
        escalatorMiddlePosition = new JoystickButton(operator, RobotMap.BUTTON_ESCALATOR_MIDDLE_POSITION);
        escalatorMiddlePosition.whileHeld(new C_SeekPosition(Escalator.ESCALATOR_MIDDLE_POSITION));
        	
        escalatorLowPosition = new JoystickButton(operator, RobotMap.BUTTON_ESCALATOR_LOW_POSITION);
        escalatorLowPosition.whileHeld(new C_SeekPosition(Escalator.ESCALATOR_LOW_POSITION));
   
        //Control Panel
        controlPanel = new Joystick(RobotMap.CONTROL_PANEL);
        
        manualControl = new JoystickButton(controlPanel, RobotMap.SWITCH_ELEVATOR_MANUAL_CONTROL);
        manualControl.whileHeld(new C_ElevatorManual());
        
    }
    
    public static Vector getDriverLeftJoystickVector() {
    	return new Vector(driver.getRawAxis(Axis.LEFT_X), -driver.getRawAxis(Axis.LEFT_Y));
    }
    
    public static Vector getDriverRightJoystickVector() {
    	return new Vector(driver.getRawAxis(Axis.RIGHT_X), -driver.getRawAxis(Axis.RIGHT_Y));
    }
    
    public static double getDriverRightX() {
        return driver.getRawAxis(Axis.RIGHT_X);
    }
    
    public static double getDriverRightY() {
        return -driver.getRawAxis(Axis.RIGHT_Y);
    }
    
    public static double getEscalatorControlAxis() {
		return operator.getRawAxis(RobotMap.AXIS_ESCALATOR_CONTROL);
    }
	
	public static class Axis {
	    public final static int LEFT_X = 0;
	    public final static int LEFT_Y = 1;
	    public final static int TRIGGER_L = 2;
	    public final static int TRIGGER_R = 3;
	    public final static int RIGHT_X = 4;
	    public final static int RIGHT_Y = 5;
	}
	
	public static class ButtonType {
		 public final static int A = 1;
		 public final static int B = 2;
		 public final static int X = 3;
		 public final static int Y = 4;
		 public final static int L1 = 5;
		 public final static int R1 = 6;
		 public final static int LEFT_THUMB = 9;
		 public final static int RIGHT_THUMB = 10;
	}
}

