package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_SlowFastDrive extends CommandGroup {
    
    public  C_SlowFastDrive() {
        addSequential(new C_AutoDrive(4, 0, 0.8));
        addSequential(new C_AutoDrive(5, 0, .7));
    }
}
