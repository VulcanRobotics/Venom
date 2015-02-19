package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiolmahon
 */
public class C_ZeroRobotHeading extends Command {
    
    public C_ZeroRobotHeading() {
        requires(Robot.swerveDrive);
    }
    
    protected void initialize() {
        Robot.swerveDrive.zeroHeading();
    }
    
    protected void execute() {
    }
    
    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {
        System.out.println("NavMXP Yaw zeroed");
    }
    
    protected void interrupted() {
    }
}
