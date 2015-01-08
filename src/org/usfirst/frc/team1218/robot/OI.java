package org.usfirst.frc.team1218.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * @author afiol-mahon
 * @author liamcook
 */
public class OI {
	
	public static Joystick xbox;
    public static Button Snake;
    public static Button GoToHeading;
    public static Button Pivot0;
    public static Button Pivot1;
    public static Bumper Pivot2;
    public static Button ZeroModules;
    public static Button ResetGyro;
    public static Button CancelZeroModules;
    public static Button ButtonUnused;
    
    public OI() {
        xbox = new Joystick(RobotMap.JOYSTICK_1);
        
        ZeroModules = new JoystickButton(xbox, ButtonType.A);
            ZeroModules.whenPressed(new org.usfirst.frc.team1218.subsystem.swerve.C_ZeroModules());
        ResetGyro = new JoystickButton(xbox, ButtonType.B);
        	ResetGyro.whenPressed(new org.usfirst.frc.team1218.subsystem.swerve.C_ResetGyro());
        CancelZeroModules = new JoystickButton(xbox, ButtonType.X);
    }
    
    
    public static double leftX() {
        return xbox.getRawAxis(Axis.LEFT_X);
    }
    
    public static double leftY() {
        return -xbox.getRawAxis(Axis.LEFT_Y);
    }
    public static double rightX() {
        return xbox.getRawAxis(Axis.RIGHT_X);
    }
    public static double rightY() {
        return -xbox.getRawAxis(Axis.RIGHT_Y);
    }
    
    public static double leftAngle() {
        return Math.toDegrees(Math.atan2(leftX(), leftY()));
    }
    
    public static double leftMagnitude() {
        return Math.sqrt(leftX() * leftX() + leftY() * leftY());
    }
    
	public class Bumper extends JoystickButton {
        GenericHID joystick;
        
        public Bumper(GenericHID joystick) {
        	super(joystick, Axis.TRIGGER_L);
            this.joystick = joystick;
        }
        
        public boolean getLeftBumper() {
            return joystick.getRawAxis(Axis.TRIGGER_L) > 0.1;
        }
        
        public boolean getRightBumper() {
        	return joystick.getRawAxis(Axis.TRIGGER_R) > 0.1;
        }
    }
	
	public static class Axis {
	    public static int LEFT_X = 0;
	    public static int LEFT_Y = 1;
	    public static int TRIGGER_L = 2;
	    public static int TRIGGER_R = 3;
	    public static int RIGHT_X = 4;
	    public static int RIGHT_Y = 5;
	}
	
	public static class ButtonType {
		 public static int A = 1;
		 public static int B = 2;
		 public static int X = 3;
		 public static int Y = 4;
		 public static int L1 = 5;
		 public static int R1 = 6;
		 public static int LEFT_THUMB = 9;
		 public static int RIGHT_THUMB = 10;
	}
}

