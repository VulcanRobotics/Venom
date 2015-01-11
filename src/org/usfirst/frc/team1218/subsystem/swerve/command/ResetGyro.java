package org.usfirst.frc.team1218.subsystem.swerve.command;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author afiolmahon
 */
public class ResetGyro extends Command {
    
    public ResetGyro() {
        requires(Robot.swerveSystem);
    }

    protected void initialize() {
        Robot.swerveSystem.resetGyro();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        System.out.println("Gyro zeroed");
    }

    protected void interrupted() {
    }
}
