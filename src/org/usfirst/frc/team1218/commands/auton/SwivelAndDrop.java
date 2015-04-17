package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwivelAndDrop extends Command {
	
	final double rotationSpeed;
	final double dropPoint;
	final double finishPoint;
	
    public SwivelAndDrop(double rotationSpeed, double dropPoint, double finishPoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    	requires(Robot.binIntake);
    	this.rotationSpeed = rotationSpeed;
    	this.dropPoint = dropPoint;
    	this.finishPoint = finishPoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveDrive.powerDrive(new Vector(0, 0), rotationSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.swerveDrive.getHeading() > dropPoint) {
    		Robot.binIntake.setClamp(BinIntake.OPEN);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.swerveDrive.getHeading() > finishPoint;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.swerveDrive.powerDrive(new Vector(0, 0), 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
