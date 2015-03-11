package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClaw;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.PowerControl;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;
import org.usfirst.frc.team1218.commands.swerve.LinearDrive;
import org.usfirst.frc.team1218.commands.swerve.MaintainRobotHeading;
import org.usfirst.frc.team1218.commands.swerve.ToggleFieldCentricDrive;
import org.usfirst.frc.team1218.commands.swerve.VisionAlign;
import org.usfirst.frc.team1218.commands.swerve.ZeroRobotHeading;
import org.usfirst.frc.team1218.commands.toteIntake.RunToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;
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
	public static Button fieldCentricToggle;
	public static Button lockDrive;
	
	//Operator
	public static Joystick operator;
	
	//Elevator
	public static Button elevatorDropStack;
	public static Button elevatorRaiseStack;
	public static Button elevatorToStepPosition;
	public static Button elevatorManualLower;
	public static Button elevatorManualRaise;
	public static Button elevatorZeroPosition;
	public static Button elevatorAutomatic;
	public static Button elevatorDisableSoftLimits;
	
	//Tote Intake
	public static Button elevatorRunToteIntake;
	public static Button elevatorReverseToteIntake;
	//Four Bar
	public static Button fourBarHighPosition;
	public static Button fourBarAutonPosition;
	public static Button fourBarGetBinFromStepPosition;
	public static Button fourBarGetNoodlePosition;
	//Bin Intake
	public static Button runBinIntake;
	public static Button openBinGrabber;
	public static Button reverseBinIntake;
	
	public static Button testButton;
	
	public OI() {
    	//Driver
    	driver = new Joystick(RobotMap.DRIVER_JOYSTICK);
    	
        resetGyro = new JoystickButton(driver, RobotMap.BUTTON_RESET_GYRO);
        resetGyro.whenPressed(new ZeroRobotHeading());
        
        maintainHeading = new JoystickButton(driver, RobotMap.BUTTON_MAINTAIN_HEADING);
        maintainHeading.whileHeld(new MaintainRobotHeading());
        
        indexSwerve = new JoystickButton(driver, RobotMap.BUTTON_INDEX_SWERVE);
        indexSwerve.whenPressed(new CalibrateModules());
        
        fieldCentricToggle = new JoystickButton(driver, RobotMap.BUTTON_FIELD_CENTRIC_TOGGLE);
        fieldCentricToggle.whenPressed(new ToggleFieldCentricDrive());
        
        lockDrive = new JoystickButton(driver, RobotMap.BUTTON_LOCK_DRIVE);
        lockDrive.whileHeld(new LinearDrive());
                
        //Operator
        operator = new Joystick(RobotMap.OPERATOR_JOYSTICK);
                
        //elevator
        elevatorManualRaise = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_RAISE);
        elevatorManualRaise.whileHeld(new PowerControl(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER));
        
        elevatorManualLower = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_LOWER);
        elevatorManualLower.whileHeld(new PowerControl(-Elevator.ELEVATOR_MANUAL_POSITIONING_POWER));
        
        elevatorAutomatic = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_AUTOMATIC);
        elevatorAutomatic.whileHeld(new AutoStack());;
        
        elevatorDisableSoftLimits = new JoystickButton(operator, BUTTON_ELEVATOR_DISABLE_SOFT_LIMITS);
        elevatorDisableSoftLimits.whenPressed(new EnableElevatorSoftLimits(false));
        
        //Tote Intake
        elevatorRunToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RUN_TOTE_INTAKE);
        elevatorRunToteIntake.whileHeld(new RunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
        
        elevatorReverseToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_REVERSE_TOTE_INTAKE);
        elevatorReverseToteIntake.whileHeld(new RunToteIntake(-ToteIntake.TOTE_INTAKE_POWER));
        
        //Four Bar
        fourBarHighPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_HIGH_POSITION);
        fourBarHighPosition.whileHeld(new SeekPosition(FourBar.PID_HIGH_POSITION));
        	
        fourBarAutonPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_AUTON_START_POSITION);
        fourBarAutonPosition.whileHeld(new SeekPosition(FourBar.PID_AUTON_START_POSITION));
        
        fourBarGetBinFromStepPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_GET_BIN_FROM_STEP_POSITION);
        fourBarGetBinFromStepPosition.whileHeld(new SeekPosition(FourBar.PID_GET_BIN_FROM_STEP_POSITION));
        
        fourBarGetNoodlePosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_GET_NOODLE_POSITION);
        fourBarGetNoodlePosition.whileHeld(new SeekPosition(FourBar.PID_GET_NOODLE_POSITION));
        
        //Bin Intake
        runBinIntake = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_RUN_BIN_INTAKE);
        runBinIntake.whenPressed(new SetBinIntake(BinIntake.INTAKE_POWER));
        runBinIntake.whenInactive(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        
        reverseBinIntake = new JoystickButton(operator, RobotMap.BUTTON_REVERSE_BIN_INTAKE);
        reverseBinIntake.whenPressed(new SetBinIntake(BinIntake.OUTPUT_POWER));
        reverseBinIntake.whenInactive(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        
        
        openBinGrabber = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_OPEN_GRABBER);
        openBinGrabber.whenPressed(new SetClaw(true));
        openBinGrabber.whenInactive(new SetClaw(false));
        
        //test button
        testButton = new JoystickButton(driver, ButtonType.X);
        testButton.whenPressed(new VisionAlign());
	}
    
    public static Vector getDriverLeftJoystickVector() {
    	return new Vector(driver.getRawAxis(Axis.LEFT_X), -driver.getRawAxis(Axis.LEFT_Y));
    }
    
    public static double getSwerveRotationAxis() {
    	return Math.pow(driver.getRawAxis(Axis.RIGHT_X), 3);
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

	public static double getTurboPower() {
		return driver.getRawAxis(RobotMap.TRIGGER_TURBO_DRIVE);
	}
}

