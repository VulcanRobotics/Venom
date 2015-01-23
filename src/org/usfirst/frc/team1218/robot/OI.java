package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.subsystem.swerve.C_ResetGyro;
import org.usfirst.frc.team1218.subsystem.swerve.C_TogglePower;
import org.usfirst.frc.team1218.subsystem.swerve.legacyModule.C_ZeroModules;

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
	
	public static Joystick xbox;
    
	public static Button cancelResetModules;
	public static Button resetGyro;
	public static Button changePower;
	public static Button zeroModules;
	
    public OI() {
        xbox = new Joystick(RobotMap.JOYSTICK_1);
        cancelResetModules = new JoystickButton(xbox, ButtonType.X);
        resetGyro = new JoystickButton(xbox, ButtonType.B);
        resetGyro.whenPressed(new C_ResetGyro());
        zeroModules = new JoystickButton(xbox, ButtonType.A);
        zeroModules.whenPressed(new C_ZeroModules());
        changePower = new JoystickButton(xbox, ButtonType.Y);
        changePower.whenPressed(new C_TogglePower());
    }
    
    public static Vector getLeftJoystickVector() {
    	return new Vector(xbox.getRawAxis(Axis.LEFT_X), -xbox.getRawAxis(Axis.LEFT_Y));
    }
    
    public static double getRightX() {
        return xbox.getRawAxis(Axis.RIGHT_X);
    }
    public static double getRightY() {
        return -xbox.getRawAxis(Axis.RIGHT_Y);
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

