package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.subsystem.elevator.C_SetElevatorPosition;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.hooks.C_DeployHooks;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.swerve.C_GoToHeading;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	
	public static Button runToteIntake;
	public static Button dropStack;
	public static Button raiseStack;
	public static Button stepPosition;
	
	public static Button runBinIntake;
	public static Button openGrabber;
	
    public OI() {
    	//Driver
    	driver = new Joystick(RobotMap.DRIVER_JOYSTICK);
    	
        resetGyro = new JoystickButton(driver, RobotMap.BUTTON_RESET_GYRO);
        	resetGyro.whenPressed(new C_ZeroRobotHeading());
        
        maintainHeading = new JoystickButton(driver, RobotMap.BUTTON_MAINTAIN_HEADING);
        	maintainHeading.whileHeld(new C_GoToHeading());
        
        //Operator
        operator = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        
        lowerHooks = new JoystickButton(operator, RobotMap.BUTTON_LOWER_HOOKS);
        	lowerHooks.whenPressed(new C_DeployHooks(true));
        	
        raiseHooks = new JoystickButton(operator, RobotMap.BUTTON_RAISE_HOOKS);
        	raiseHooks.whenPressed(new C_DeployHooks(false));
        	
        runToteIntake = new JoystickButton(operator, RobotMap.BUTTON_RUN_TOTE_INTAKE);
        	
        dropStack = new JoystickButton(operator, RobotMap.BUTTON_DROP_STACK);
        	dropStack.whenPressed(new C_SetElevatorPosition(Elevator.ELEVATOR_DROP_POSITION));
        	
        raiseStack = new JoystickButton(operator, RobotMap.BUTTON_RAISE_STACK);
        	raiseStack.whenPressed(new C_SetElevatorPosition(Elevator.ELEVATOR_RAISE_POSITION));
        	
        stepPosition = new JoystickButton(operator, RobotMap.BUTTON_STEP_POSITION);
        	stepPosition.whenPressed(new C_SetElevatorPosition(Elevator.ELEVATOR_STEP_POSITION));
        	
        runBinIntake = new JoystickButton(operator, RobotMap.BUTTON_RUN_BIN_INTAKE);
        
        openGrabber = new JoystickButton(operator, RobotMap.BUTTON_OPEN_GRABBER);
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

