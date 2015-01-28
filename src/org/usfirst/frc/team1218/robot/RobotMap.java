package org.usfirst.frc.team1218.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Swerve Drive
	public static final int[] SM_DRIVE_MOTOR = {10, 12, 14, 16};
	public static final int[] SM_TURN_MOTOR = {11, 13, 15, 17};
	public static final int[] SM_ANGLE_ENCODER_A = {0, 3, 6, 9};
	public static final int[] SM_ANGLE_ENCODER_B = {1, 4, 7, 10};
	public static final int[] SM_ANGLE_ENCODER_I = {2, 5, 8, 11};
	//Elevator
	public static final int ELEVATOR_LIFT_MASTER = -1;
	public static final int ELEVATOR_LIFT_SLAVE = -1;
	public static final int ELEVATOR_INTAKE_L = -1;
	public static final int ELEVATOR_INTAKE_R = -1;
	//Escalator
	public static final int ESCALATOR_LEFT_DART = -1;
	public static final int ESCALATOR_RIGHT_DART = -1;
	public static final int ESCALATOR_INTAKE_L = -1;
	public static final int ESCALATOR_INTAKE_R = -1;
	public static final int ESCALATOR_CLAMP_SOLENOID = -1;
	//Hooks
	public static final int HOOK_DEPLOY_SOLENOID = -1;
	//Control
	public static final int DRIVER_JOYSTICK = 0;
	public static final int OPERATOR_JOYSTICK = 1;
}
