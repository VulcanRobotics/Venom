package org.usfirst.frc.team1218.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * @author afiol-mahon
 * @author liamcook
 */
public class OI {
	
	public static Joystick xbox;
    
    public OI() {
        xbox = new Joystick(RobotMap.JOYSTICK_1);
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

