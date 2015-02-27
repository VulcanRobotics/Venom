package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_ModuleGoToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive in a cute little square
 * @author afiol-mahon
 */
public class C_MovementTest extends CommandGroup {
    
    public  C_MovementTest() {
        addSequential(new C_AutoDrive(3, 0, .6));
        addSequential(new C_ModuleGoToAngle(270));
        addSequential(new C_AutoDrive(3, 270, .6));
        addSequential(new C_ModuleGoToAngle(180));
        addSequential(new C_AutoDrive(3, 180, .6));
        addSequential(new C_ModuleGoToAngle(90));
        addSequential(new C_AutoDrive(3, 90, .6));
    }
}
