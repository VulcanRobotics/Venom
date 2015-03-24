package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class GoToHeading extends Command {

	double heading;
	
    public GoToHeading(double heading) {
    	requires(Robot.swerveDrive);
    	this.heading = heading;
    }

    protected void initialize() {
    	Robot.swerveDrive.enableHeadingController(heading);
    }

    protected void execute() {
    	Robot.swerveDrive.powerDrive(new Vector(0, 0), 0);
    }

    protected boolean isFinished() {
        return Robot.swerveDrive.isHeadingOnTarget();
    }

    protected void end() {
    	Robot.swerveDrive.disableHeadingController();
    }

    protected void interrupted() {
    	end();
    }
}
