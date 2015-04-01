package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.VisionAlign;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveVision extends CommandGroup {
    
    public  AutoDriveVision(double distance, double direction, double heading, double power) {
        addSequential(new AutoDrive(distance, direction, heading, power));
        addSequential(new VisionAlign());
    }
}
