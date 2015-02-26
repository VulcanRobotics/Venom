package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveToBreakbeam extends Command {

	double heading;
	
	double DRIVE_POWER = 0.75;
	
    public C_DriveToBreakbeam(double robotCentricHeading) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.swerveDrive);
        heading = robotCentricHeading;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.swerveDrive.isAnglePIDOnTarget()) {
    		Robot.swerveDrive.setModuleAngles(heading);
    	}
    	Robot.swerveDrive.enableHeadingController(0); //point towards zero
    	Robot.swerveDrive.setFieldCentricDriveMode(false);
    	Vector translationVector = new Vector(1, 1);
    	translationVector.setAngle(heading);
    	translationVector.setMagnitude(DRIVE_POWER);
    	Robot.swerveDrive.powerDrive(translationVector, 0);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.swerveDrive.getBreakbeam();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
