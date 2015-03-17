package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.swerve.AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiolmahon
 */
public class Auton_JustDrive extends CommandGroup {
    
    public  Auton_JustDrive() {
    	addSequential(new Auton_Calibrate());
    	String direction = SmartDashboard.getString("Calibration_Orientation", "North");
    	switch (direction) {
    		default: addSequential(new AutoDrive(10, 0, 0, 1.0));
    			break;
    		case "North": addSequential(new AutoDrive(10, 0, 0, 1.0));
    			break;
    		case "South": addSequential(new AutoDrive(10, 180, 0, 1.0));
    			break;
    		case "East": addSequential(new AutoDrive(10, 90, 0, 1.0));
    			break;
    		case "West":addSequential(new AutoDrive(10, 270, 0, 1.0));
    			break;
    	}
        
    }
}
