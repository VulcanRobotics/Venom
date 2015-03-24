package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@author afiolmahon
 *@author lcook
 */
public class Auton_JustDrive extends CommandGroup {
    
    public  Auton_JustDrive() {
    	addSequential(new Auton_Calibrate());
    	addSequential(new AutoDrive(10, 0, Robot.swerveDrive.getHeading(), 1.0));
    }
}
