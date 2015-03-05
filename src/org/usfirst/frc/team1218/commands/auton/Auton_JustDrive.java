package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.CalibrateOrientation;
import org.usfirst.frc.team1218.commands.swerve.ZeroRobotHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_JustDrive extends CommandGroup {
    
    public  Auton_JustDrive() {
        addSequential(new ZeroRobotHeading());
        addSequential(new CalibrateOrientation());
        addSequential(new AutoDrive(10, 90, 1.0));
    }
}
