package org.usfirst.frc.team1218.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * @author afiol-mahon
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
	
	//Driver Mapping
	public static final int DRIVER_JOYSTICK = 0;
	public static final int BUTTON_RESET_GYRO = OI.ButtonType.B;
	public static final int BUTTON_MAINTAIN_HEADING = OI.ButtonType.L1;
	
	//Operator Mapping
	public static final int OPERATOR_JOYSTICK = 1;
	
	public static final int BUTTON_LOWER_HOOKS = 7;
	public static final int BUTTON_RAISE_HOOKS = 9;
	
	public static final int BUTTON_ELEVATOR_RUN_TOTE_INTAKE = 3;
	public static final int BUTTON_ELEVATOR_DROP_STACK = 4;
	public static final int BUTTON_ELEVATOR_RAISE_STACK = 6;
	public static final int BUTTON_ELEVATOR_STEP_POSITION = 5;
	
	public static final int AXIS_ESCALATOR_CONTROL = 1;
	public static final int BUTTON_ESCALATOR_RUN_BIN_INTAKE = 2;
	public static final int BUTTON_ESCALATOR_OPEN_GRABBER = 1;
	public static final int BUTTON_ESCALATOR_HIGH_POSITION = 8;
	public static final int BUTTON_ESCALATOR_MIDDLE_POSITION = 10;
	public static final int BUTTON_ESCALATOR_LOW_POSITION = 12;
}
