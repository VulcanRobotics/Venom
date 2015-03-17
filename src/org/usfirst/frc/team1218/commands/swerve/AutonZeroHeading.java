package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonZeroHeading extends Command {

    public AutonZeroHeading() {
        requires(Robot.swerveDrive);
    }

    protected void initialize() {
    	Robot.swerveDrive.autonZeroHeading();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        System.out.println("NavMXP Yaw zeroed and offset from dashboard");

    }

    protected void interrupted() {
    }
}
