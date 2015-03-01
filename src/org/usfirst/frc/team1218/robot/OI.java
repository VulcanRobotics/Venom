package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetBinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetClaw;
import org.usfirst.frc.team1218.subsystem.elevator.C_ManualControl;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.fourBar.C_FourBarDefault;
import org.usfirst.frc.team1218.subsystem.fourBar.C_ReevaluateDartSafety;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_LockDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_MaintainHeading;
import org.usfirst.frc.team1218.subsystem.swerve.C_ToggleFieldCentricDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_RunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	//Tote Intake
	public static Button elevatorRunToteIntake;
	public static Button elevatorReverseToteIntake;
	//Four Bar
	public static Button fourBarHighPosition;
	public static Button fourBarLowPosition;
	
	public static Button fourBarGetBinFromStepPosition;
	public static Button fourBarGetNoodlePosition;
	
	public static ButtonReevaluateDartSafety reevaluateDartSafety;
	
	//Bin Intake
	public static Button runBinIntake;
	public static Button openBinGrabber;
	
	public static Button test;
	public static Button test2;
	
	public OI() {
    	//Driver
    	driver = new Joystick(RobotMap.DRIVER_JOYSTICK);
    	
        resetGyro = new JoystickButton(driver, RobotMap.BUTTON_RESET_GYRO);
        resetGyro.whenPressed(new C_ZeroRobotHeading());
        
        maintainHeading = new JoystickButton(driver, RobotMap.BUTTON_MAINTAIN_HEADING);
        maintainHeading.whileHeld(new C_MaintainHeading());
        
        indexSwerve = new JoystickButton(driver, RobotMap.BUTTON_INDEX_SWERVE);
        indexSwerve.whenPressed(new C_Index());
        
        fieldCentricToggle = new JoystickButton(driver, RobotMap.BUTTON_FIELD_CENTRIC_TOGGLE);
        fieldCentricToggle.whenPressed(new C_ToggleFieldCentricDrive());
        
        lockDrive = new JoystickButton(driver, RobotMap.BUTTON_LOCK_DRIVE);
        lockDrive.whileHeld(new C_LockDrive());
                
        //Operator
        operator = new Joystick(RobotMap.OPERATOR_JOYSTICK);
                
        elevatorManualRaise = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_RAISE);
        elevatorManualRaise.whileHeld(new C_ManualControl(Elevator.ELEVATOR_MANUAL_POSITIONING_POWER));
        
        elevatorManualLower = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_MANUAL_LOWER);
        elevatorManualLower.whileHeld(new C_ManualControl(-Elevator.ELEVATOR_MANUAL_POSITIONING_POWER));
        
        //Tote Intake
        elevatorRunToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_RUN_TOTE_INTAKE);
        elevatorRunToteIntake.whileHeld(new C_RunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
        
        elevatorReverseToteIntake = new JoystickButton(operator, RobotMap.BUTTON_ELEVATOR_REVERSE_TOTE_INTAKE);
        elevatorReverseToteIntake.whileHeld(new C_RunToteIntake(-ToteIntake.TOTE_INTAKE_POWER));
        
        //Four Bar
        fourBarHighPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_HIGH_POSITION);
        fourBarHighPosition.whileHeld(new C_SeekPosition(FourBar.FOUR_BAR_HIGH_POSITION));
        	
        fourBarLowPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_LOW_POSITION);
        fourBarLowPosition.whileHeld(new C_SeekPosition(FourBar.FOUR_BAR_LOW_POSITION));
        
        fourBarGetBinFromStepPosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_GET_BIN_FROM_STEP_POSITION);
        fourBarGetBinFromStepPosition.whileHeld(new C_SeekPosition(FourBar.FOUR_BAR_GET_BIN_FROM_STEP_POSITION));
        
        fourBarGetNoodlePosition = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_GET_NOODLE_POSITION);
        fourBarGetNoodlePosition.whileHeld(new C_SeekPosition(FourBar.FOUR_BAR_GET_NOODLE_POSITION));
        
        reevaluateDartSafety = new ButtonReevaluateDartSafety();
        reevaluateDartSafety.whenActive(new C_ReevaluateDartSafety());
        
        //Bin Intake
        runBinIntake = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_RUN_BIN_INTAKE);
        runBinIntake.whenPressed(new C_SetBinIntake(BinIntake.INTAKE_POWER));
        runBinIntake.whenInactive(new C_SetBinIntake(0.1));
        
        openBinGrabber = new JoystickButton(operator, RobotMap.BUTTON_FOUR_BAR_OPEN_GRABBER);
        openBinGrabber.whenPressed(new C_SetClaw(true));
        openBinGrabber.whenInactive(new C_SetClaw(false));
        
        test2 = new JoystickButton(driver, OI.ButtonType.Y);
        test2.whenPressed(new C_SeekPosition(0.6));
        test2.whenReleased(new C_FourBarDefault());
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

	public static double getTurboPower() {
		return driver.getRawAxis(RobotMap.TRIGGER_TURBO_DRIVE);
	}
	
	public class ButtonReevaluateDartSafety extends Trigger {

		@Override
		public boolean get() {
			return SmartDashboard.getBoolean("Reevaluate_Dart_Safety", false);
		}
	}
}

