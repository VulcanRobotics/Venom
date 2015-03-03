package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_JustDrive extends CommandGroup {
    
    public  C_JustDrive() {
        addSequential(new C_ZeroRobotHeading());
        addSequential(new C_Index());
        addSequential(new C_AutoDrive(10, 90, 1.0));
    }
}
