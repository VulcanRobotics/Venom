package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetFieldCentric extends Command {

	boolean fieldCentric;
	
    public SetFieldCentric(boolean fieldCentric) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.fieldCentric = fieldCentric;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveDrive.setFieldCentricDriveMode(fieldCentric);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
