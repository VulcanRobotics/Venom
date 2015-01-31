package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.RobotMap;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Swerve Class that handles all module logic except for the angle writing itself
 * @author afiol-mahon
 */
public abstract class SwerveModule {
	
	protected final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	protected double robotCentricAngle = 0; //Angle relative to front of robot
	protected boolean invertModule = false;
	protected final CANTalon driveWheelController;
	private static final double DRIVE_POWER_SCALE = 0.4;
	protected static final boolean[] MODULE_REVERSED = {false, false, true, true};
	
	public SwerveModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveWheelController = new CANTalon(RobotMap.SM_DRIVE_MOTOR[moduleNumber]);
		
	}
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setValues(double angle, double power) {
		if (Math.abs(power) > 0.1) writeRobotCentricAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		setPower(power);
	}
	
	public void setVector(Vector vector) {
		setValues(vector.getAngle(), vector.getMagnitude());
	}
	
	public void writeRobotCentricAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.robotCentricAngle) > 90) invertModule = !invertModule;
		this.robotCentricAngle = angle;
		angle += (invertModule) ? 180 : 0;
		angle = (MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle;
		angle = Angle.get360Angle(angle);
		setRealAngle(angle);
	}
	
	/**
	 * Method that controls final stage of setting a properly offset angle
	 * to the swerve drive.
	 * The purpose of this is to allow the swerveModule to handle as much of
	 * the uniform calculations that happen in both modules as possible
	 * so that both SwerveModule Classes are as small as possible
	 * so they just have to define the last part of the angle write
	 * @param angle Desired wheel angle. Can be any value
	 */
	public abstract void setRealAngle(double angle);
	
	public void setPower(double power) {
		if (Math.abs(power) > 1){
			System.out.println("Illegal power " + power + " written to module: " + moduleNumber);
		} else {
			power *= (invertModule) ? -1.0 : 1.0;
			this.driveWheelController.set(DRIVE_POWER_SCALE * power * ((MODULE_REVERSED[moduleNumber]) ? 1.0 : -1.0)); //Applies module specific motor preferences
		}
	}
	
	public CANTalon getDriveWheelController() {
		return driveWheelController;
	}
	
	public void syncDashboard() {//TODO fix driver station value keys
		SmartDashboard.putNumber("SM_" + moduleNumber + "_WheelPower", driveWheelController.get());
	}
	
}
