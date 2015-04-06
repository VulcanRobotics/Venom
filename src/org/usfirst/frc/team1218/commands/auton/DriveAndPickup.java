package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAndPickup extends CommandGroup {
   	
    public  DriveAndPickup(double distance, double direction, double heading, double power) {
    	addParallel(new AutoDrive(distance, direction, heading, power));
    	addParallel(new AutoStack(1));
    }
}
