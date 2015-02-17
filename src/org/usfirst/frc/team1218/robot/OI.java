package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.subsystem.elevator.C_GoToBottom;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToClearance;
import org.usfirst.frc.team1218.subsystem.elevator.C_ManualControl;
import org.usfirst.frc.team1218.subsystem.elevator.C_PickupTote;
import org.usfirst.frc.team1218.subsystem.elevator.C_ReferenceLimit;
import org.usfirst.frc.team1218.subsystem.elevator.C_SetElevatorSetpoint;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.hooks.C_DeployHooks;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_MaintainHeading;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_RunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

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
	public static Button indexSwerve;
	public static Button robotCentricToggle;
	public static Button deployHooks;
	
	//Operator
	public static Joystick operator;
	
	//Elevator
	public static Button elevatorDropStack;
	public static Button elevatorRaiseStack;
	public static Button elevatorToStepPosition;
	public static Button elevatorManualLower;
	public static Button elevatorManualRaise;
	public static Button elevatorZeroPosition;
	//Tote Intake
	public static Button elevatorRunToteIntake;
	public static Button elevatorReverseToteIntake;
	//Four Bar
	public static Button fourBarRunBinIntake;
	public static Button fourBarOpenGrabber;
	public static Button fourBarHighPosition;
	public static Button fourBarLowPosition;
	
	//test
	public static Button testButton;
	
	public OI() {
    	//Driver
    	driver = new Joystick(RobotMap.DRIVER_JOYSTICK);
    	
        resetGyro = new JoystickButton(driver, RobotMap.BUTTON_RESET_GYRO);
        resetGyro.whenPressed(new C_ZeroRobotHeading());
        
        maintainHeading = new JoystickButton(driver, RobotMap.BUTTON_MAINTAIN_HEADING);
        maintainHeading.whileHeld(new C_MaintainHeading());
        
        indexSwerve = new JoystickButton(driver, RobotMap.BUTTON_INDEX_SWERVE);
        indexSwerve.whenPressed(new C_Index());
        
        robotCentricToggle = new JoystickButton(driver, RobotMap.BUTTON_ROBOT_CENTRIC_TOGGLE);
        
        deployHooks = new JoystickButton(driver, RobotMap.BUTTON_DEPLOY_HOOKS);
        deployHooks.whileHeld(new C_DeployHooks());
        
        //Operator
        operator = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        
        //Elevator
        elevatorDropStack = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_DROP_STACK);
        elevatorDropStack.whenPressed(new C_SetElevatorSetpoint(Elevator.ELEVATOR_DROP_POSITION));
        	
        elevatorRaiseStack = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RAISE_STACK);
        elevatorRaiseStack.whenPressed(new C_SetElevatorSetpoint(Elevator.ELEVATOR_RAISE_POSITION));
        	
        elevatorToStepPosition = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_STEP_POSITION);
        elevatorToStepPosition.whenPressed(new C_SetElevatorSetpoint(Elevator.ELEVATOR_STEP_POSITION));
        
        elevatorManualRaise = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_RAISE);
        elevatorManualRaise.whileHeld(new C_ManualControl(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER));
        
        elevatorManualLower = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_LOWER);
        elevatorManualLower.whileHeld(new C_ManualControl(-Elevator.ELEVATOR_MANUAL_POSITIONING_POWER));
        
        elevatorZeroPosition = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_ZERO_POSITION);
        elevatorZeroPosition.whenPressed(new C_ReferenceLimit());
        
        //Tote Intake
        elevatorRunToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RUN_TOTE_INTAKE);
        elevatorRunToteIntake.whileHeld(new C_RunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
        
        elevatorReverseToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_REVERSE_TOTE_INTAKE);
        elevatorReverseToteIntake.whileHeld(new C_RunToteIntake(-ToteIntake.TOTE_INTAKE_POWER));
        
        //Four Bar
        fourBarRunBinIntake = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_RUN_BIN_INTAKE);
        
        fourBarOpenGrabber = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_OPEN_GRABBER);
        
        fourBarHighPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_HIGH_POSITION);
        fourBarHighPosition.whileHeld(new C_SeekPosition(FourBar.FOUR_BAR_HIGH_POSITION));
        	
        fourBarLowPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_LOW_POSITION);
        fourBarLowPosition.whileHeld(new C_SeekPosition(FourBar.FOUR_BAR_LOW_POSITION));
    
        //test
        testButton = new JoystickButton(driver, ButtonType.X);
        testButton.whenPressed(new C_PickupTote());
        //testButton.whenPressed(new C_GoToClearance());
        //testButton.whenPressed(new C_AutoDrive(new Vector(5, 0), 0));
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
    
    public static double getFourBarControlAxis() {
		return operator.getRawAxis(RobotMap.AXIS_FOUR_BAR_CONTROL);
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

