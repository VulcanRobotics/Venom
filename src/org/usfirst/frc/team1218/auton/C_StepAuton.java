package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.autonHooks.C_DeployHooks;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToTop;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_StepAuton extends CommandGroup {
    
    public  C_StepAuton() {
    	addSequential(new C_Index());
    	addSequential(new C_GoToTop());
    	addSequential(new C_AutoDrive(-6, 0, 1));
    	addSequential(new C_DeployHooks(true));
    	addSequential(new C_AutoDrive(10, 0, 1));
    	addSequential(new C_DeployHooks(false));

    }
}
