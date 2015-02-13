/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author afiolmahon
 */
public class C_ZeroRobotHeading extends Command {
    
    public C_ZeroRobotHeading() {
        requires(Robot.swerveDrive);
    }
    
    protected void initialize() {
        Robot.swerveDrive.navModule.zeroYaw();
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
