package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.ReferenceElevator;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;
import org.usfirst.frc.team1218.commands.swerve.MaintainRobotHeading;
import org.usfirst.frc.team1218.commands.swerve.TankDrive;
import org.usfirst.frc.team1218.commands.swerve.ToggleFieldCentricDrive;
import org.usfirst.frc.team1218.commands.swerve.VisionAlign;
import org.usfirst.frc.team1218.commands.swerve.ZeroRobotHeading;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
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
	public static Button calibrateSwerve;
	public static Button fieldCentricToggle;
	public static Button tankDrive;
	
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
	
	public static Button toteIntakeFromLeft;
	public static Button toteIntakeFromRight;
	
	//Four Bar
	public static Button fourBarHighPosition;
	public static Button fourBarAutonPosition;
	//Bin Intake
	public static Button runBinIntake;
	public static Button openBinGrabber;
	public static Button reverseBinIntake;
	
	public static Button referenceElevator;
	//test
	public static Button test;
	
	public static Button upDPad;
	public static Button leftDPad;
	public static Button rightDPad;
	public static Button downDPad;
	public OI() {
    	//Driver
    	driver = new Joystick(RobotMap.DRIVER_JOYSTICK);
    	
        resetGyro = new JoystickButton(driver, RobotMap.BUTTON_RESET_GYRO);
        resetGyro.whenPressed(new ZeroRobotHeading());
        
        maintainHeading = new JoystickButton(driver, RobotMap.BUTTON_MAINTAIN_HEADING);
        maintainHeading.whileHeld(new MaintainRobotHeading());
        
        calibrateSwerve = new JoystickButton(driver, RobotMap.BUTTON_INDEX_SWERVE);
        calibrateSwerve.whenPressed(new CalibrateModules());
        
        fieldCentricToggle = new JoystickButton(driver, RobotMap.BUTTON_FIELD_CENTRIC_TOGGLE);
        fieldCentricToggle.whenPressed(new ToggleFieldCentricDrive());
        
        tankDrive = new JoystickButton(driver, RobotMap.BUTTON_TANK_DRIVE);
        tankDrive.whileHeld(new TankDrive());
        /*
        //forward d-pad, set field centric
        //upDPad = ?
        upDPad.whenPressed(new SetFieldHeading(0));
        upDPad.whenPressed(new EnableMaintainHeading(false));
        upDPad.whenPressed(new SetFieldCentric(true));

        //left d-pad, set left chute door  centric
		//leftDPad = ?
        leftDPad.whenPressed(new SetFieldHeading(Robot.swerveDrive.LEFT_CHUTE_DOOR_ANGLE));
        leftDPad.whenPressed(new EnableMaintainHeading(true));
        leftDPad.whenPressed(new SetFieldCentric(true));

       //right d-pad, set right chute door  centric
		//rightDPad = ?
        rightDPad.whenPressed(new SetFieldHeading(Robot.swerveDrive.RIGHT_CHUTE_DOOR_ANGLE));
        rightDPad.whenPressed(new EnableMaintainHeading(true));
        rightDPad.whenPressed(new SetFieldCentric(true));

        //down d-pad, set robot centric
		//downDPad = ?
        downDPad.whenPressed(new SetFieldHeading(0));
        downDPad.whenPressed(new EnableMaintainHeading(false));
        downDPad.whenPressed(new SetFieldCentric(false));
            */    
         //Operator
        operator = new Joystick(RobotMap.OPERATOR_JOYSTICK);
                
        //Elevator
        elevatorManualRaise = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_RAISE);        
        elevatorManualLower = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_LOWER);
        
        elevatorAutomatic = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_AUTOMATIC);
        elevatorAutomatic.whileHeld(new AutoStack());
        
        referenceElevator = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_REFERENCE);
        referenceElevator.whileHeld(new ReferenceElevator());
        
        //Tote Intake
        elevatorRunToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RUN_TOTE_INTAKE);
        elevatorRunToteIntake.whenPressed(new SetToteIntake(true, true, ToteIntake.TOTE_INTAKE_POWER));
        elevatorRunToteIntake.whenReleased(new SetToteIntake(true, true, 0.0));
        
        elevatorReverseToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_REVERSE_TOTE_INTAKE);
        elevatorReverseToteIntake.whenPressed(new SetToteIntake(true, true, -ToteIntake.TOTE_INTAKE_POWER));
        elevatorReverseToteIntake.whenReleased(new SetToteIntake(true, true, 0.0));
        
        toteIntakeFromLeft = new JoystickButton(operator, RobotMap.BUTTON_TOTE_INTAKE_FROM_LEFT);
        toteIntakeFromLeft.whenPressed(new SetToteIntake(true, false, ToteIntake.TOTE_INTAKE_POWER));
        toteIntakeFromLeft.whenReleased(new SetToteIntake(true, false, 0.0));
        
        toteIntakeFromRight = new JoystickButton(operator, RobotMap.BUTTON_TOTE_INTAKE_FROM_RIGHT);
        toteIntakeFromRight.whenPressed(new SetToteIntake(false, true, ToteIntake.TOTE_INTAKE_POWER));
        toteIntakeFromRight.whenReleased(new SetToteIntake(false, true, 0.0));
        
        //Four Bar
        fourBarHighPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_HIGH_POSITION);
        fourBarHighPosition.whileHeld(new SeekPosition(FourBar.PID_HIGH_POSITION));
        	
        fourBarAutonPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_AUTON_START_POSITION);
        fourBarAutonPosition.whileHeld(new SeekPosition(FourBar.PID_AUTON_START_POSITION));
        
        //Bin Intake
        runBinIntake = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_RUN_BIN_INTAKE);
        runBinIntake.whenPressed(new SetBinIntake(BinIntake.INTAKE_POWER));
        runBinIntake.whenInactive(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        
        reverseBinIntake = new JoystickButton(operator, RobotMap.BUTTON_REVERSE_BIN_INTAKE);
        reverseBinIntake.whenPressed(new SetBinIntake(BinIntake.OUTPUT_POWER));
        reverseBinIntake.whenInactive(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        
        openBinGrabber = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_OPEN_GRABBER);
        openBinGrabber.whenPressed(new SetClamp(true));
        openBinGrabber.whenInactive(new SetClamp(false));
        
        //test button
        test = new JoystickButton(driver, ButtonType.X);
        //test.whenPressed(new Auton_TwoTote());
        test.whenPressed(new VisionAlign());
        //test.whenPressed(new AutoStack(1));
        //test.whenPressed(new AutoDrive(4.0, 0, 0, 1.5));
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

