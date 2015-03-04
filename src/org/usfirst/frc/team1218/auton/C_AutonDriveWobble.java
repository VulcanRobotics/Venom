package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_AutonDriveWobble extends CommandGroup {
    
    public  C_AutonDriveWobble(double distance, double driveHeading, double maxPower) {
    	//addParallel(new C_Wobble());
       addSequential(new C_AutoDrive(distance, driveHeading, maxPower));
    }
}
