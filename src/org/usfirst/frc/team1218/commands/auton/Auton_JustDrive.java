package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.swerve.AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@author afiolmahon
 */
public class Auton_JustDrive extends CommandGroup {
    
    public  Auton_JustDrive() {
    	addSequential(new Auton_Calibrate());
    	addSequential(new AutoDrive(10, 0, 0, 1.0));
    }
}
