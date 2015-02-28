package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.swerve.C_Index;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_ZeroAuton extends CommandGroup {
    
    public  C_ZeroAuton() {
        addSequential(new C_Index());
    }
}
